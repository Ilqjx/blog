package com.upfly.dao;

import java.util.List;

import com.upfly.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByBlogId(Long blogId, Sort sort);

}
