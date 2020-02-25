package com.upfly.service;

import com.upfly.po.Blog;
import com.upfly.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    public Blog saveBlog(Blog blog);

    public Blog getBlog(Long id);

    public Blog updateBlog(Long id, Blog blog);

    public void deleteBlog(Long id);

    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

}
