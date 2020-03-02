package com.upfly.service.impl;

import javax.persistence.criteria.*;

import java.util.*;

import com.sun.org.apache.xalan.internal.xsltc.trax.XSLTCSource;
import com.upfly.dao.BlogRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.po.Blog;
import com.upfly.po.Type;
import com.upfly.service.BlogService;
import com.upfly.util.MarkdownUtil;
import com.upfly.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        blog.setViews(0);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        try {
            blogOptional.get();
        } catch (Exception e) {
            return null;
        }
        return blogOptional.get();
    }

    @Override
    @Transactional
    public Blog getAndConvertBlog(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        try {
            blogOptional.get();
        } catch (Exception e) {
            throw new NotFoundException("该博客不存在");
        }
        blogRepository.updateViews(id);

        Blog blog = getBlog(id);
        Blog tempBlog = new Blog();
        BeanUtils.copyProperties(blog, tempBlog);
        String content = tempBlog.getContent();
        String htmlContent =  MarkdownUtil.markdownToHtmlExtensions(content);
        tempBlog.setContent(htmlContent);
        return tempBlog;
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog tempBlog = getBlog(id);
        if (tempBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog, tempBlog, "views", "createTime");
        return blogRepository.save(tempBlog);
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.delete(getBlog(id));
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {

            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                // 关联查询
                Join join = root.join("tagList");
                // join.get("id"): tagId
                return cb.equal(join.get("id"), tagId);
            }

        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {

            @Override
            // 处理动态查询条件的
            // root: 代表查询的对象是blog 把blog对象映射成root 可以获取表的字段
            // criteriaQuery: 查询条件的容器
            // criteriaBuilder: 设置一个具体条件的表达式
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                // 查询条件放在List里面
                // predicate就是封装好的条件
                List<Predicate> predicateList = new ArrayList<>();
                // 标题(title)不为空 有条件
                 if (blogQuery.getTitle() != null && !"".equals(blogQuery.getTitle())) {
                     // 模糊查询
                     // arg1: 属性的名字(title)
                     // arg2: 属性值
                     predicateList.add(cb.like(root.<String>get("title"), "%" + blogQuery.getTitle() + "%"));
                 }
                 // 分类不为空 有条件
                 if (blogQuery.getTypeId() != null) {
                     // root.<Type>get("type").get("id") 先取到type对象 在取type对象的id
                     predicateList.add(cb.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
                 }
                 // 推荐
                if (blogQuery.isRecommend()) {
                    predicateList.add(cb.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
                }
                // 查询
                cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }

        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> listArchiveBlog() {
        Map<String, List<Blog>> archiveBlogMap = new LinkedHashMap<>();
        List<String> yearList = blogRepository.findGroupByYear();
        for (String year : yearList) {
            List<Blog> blogList = blogRepository.findByYear(year);
            archiveBlogMap.put(year, blogList);
        }
        return archiveBlogMap;
    }

}
