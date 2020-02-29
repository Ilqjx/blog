package com.upfly.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.upfly.dao.CommentRepository;
import com.upfly.po.Comment;
import com.upfly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment getComment(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        try {
            commentOptional.get();
        } catch (Exception e) {
            return null;
        }
        return commentOptional.get();
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
//        if (parentCommentId != -1) {
//            Comment parentComment = getComment(parentCommentId);
//            comment.setParentComment(parentComment);
//        } else {
//            comment.setParentComment(null);
//        }
        if (parentCommentId == -1) {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
    }

}
