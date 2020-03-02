package com.upfly.service;

import java.util.List;

import com.upfly.po.Blog;
import com.upfly.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    public Blog saveBlog(Blog blog);

    public Blog getBlog(Long id);

    public Blog getAndConvertBlog(Long id);

    public Blog updateBlog(Long id, Blog blog);

    public void deleteBlog(Long id);

    public List<Blog> listRecommendBlogTop(Integer size);

    public Page<Blog> listBlog(Pageable pageable);

    public Page<Blog> listBlog(Long tagId, Pageable pageable);

    public Page<Blog> listBlog(String query, Pageable pageable);

    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

}
