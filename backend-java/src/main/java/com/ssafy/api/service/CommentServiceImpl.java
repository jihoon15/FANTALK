package com.ssafy.api.service;

import com.ssafy.api.request.CommentRegisterPostReq;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 댓글 서비스 구현체 정의
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    // 댓글 등록
    @Override
    public void registerComment(CommentRegisterPostReq commentInfo, Article article, User user) {

        try {
            Comment comment = Comment.builder()
                    .content(commentInfo.getContent())
                    .user(user)
                    .article(article)
                    .build();
            commentRepository.save(comment);
            article.addCommentList(comment);
        } catch (Exception e) {
            throw e;
        }
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
