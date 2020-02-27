package com.upfly.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.upfly.dao.BlogRepository;
import com.upfly.exception.NotFoundException;
import com.upfly.po.Blog;
import com.upfly.po.Type;
import com.upfly.service.BlogService;
import com.upfly.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
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
    public Blog updateBlog(Long id, Blog blog) {
        Blog tempBlog = getBlog(id);
        if (tempBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        blog.setViews(tempBlog.getViews());
        blog.setCreateTime(tempBlog.getCreateTime());
        blog.setUpdateTime(new Date());
        BeanUtils.copyProperties(blog, tempBlog);
        return blogRepository.save(tempBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(getBlog(id));
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

}
