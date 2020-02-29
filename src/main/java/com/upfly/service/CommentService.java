package com.upfly.service;

import java.util.List;

import com.upfly.po.Comment;

public interface CommentService {

    public Comment getComment(Long id);

    public Comment saveComment(Comment comment);

    public List<Comment> listCommentByBlogId(Long blogId);

}
