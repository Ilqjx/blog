package com.upfly.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.upfly.dao.CommentRepository;
import com.upfly.po.Comment;
import com.upfly.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // 存放迭代找出的所有子代的集合
    private List<Comment> tempReplyCommentList = new ArrayList<>();

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
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(commentList);
    }

    /**
     * copy commentlist
     * @param commentList
     * @return
     */
    private List<Comment> eachComment(List<Comment> commentList) {
        List<Comment> copyCommentList = new ArrayList<>();
        for (Comment comment : commentList) {
            Comment tempComment = new Comment();
            BeanUtils.copyProperties(comment, tempComment);
            copyCommentList.add(tempComment);
        }
        combineChildren(copyCommentList);
        return copyCommentList;
    }

    /**
     * 合并评论的各层子代到第一级子代集合中
     * @param commentList parentComment不为空的comment对象集合
     */
    private void combineChildren(List<Comment> commentList) {
        for (Comment comment : commentList) {
            List<Comment> replyCommentList = comment.getReplyCommentList();
            for (Comment replyComment : replyCommentList) {
                // 循环迭代 找出子代存放在tempReplyCommentList中
                recursively(replyComment);
            }
            // 修改顶级节点的replyCommentList为迭代处理后的集合
            comment.setReplyCommentList(tempReplyCommentList);
            // 清空临时存放区
            tempReplyCommentList = new ArrayList<>();
        }
    }

    /**
     * 递归迭代
     * @param comment 迭代对象
     */
    private void recursively(Comment comment) {
        // 添加节点到临时存放区
        tempReplyCommentList.add(comment);
        List<Comment> replyCommentList = comment.getReplyCommentList();
        if (replyCommentList.size() > 0) {
            for (Comment replyComment : replyCommentList) {
                recursively(replyComment);
            }
        }
    }

}
