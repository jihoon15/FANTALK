package com.ssafy.api.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.ArticleRegisterPostReq;
import com.ssafy.api.response.ArticleDetailGetRes;
import com.ssafy.api.response.ArticleListGetRes;
import com.ssafy.api.response.CommentDetailGetRes;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 게시글 서비스 구현체 정의
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final CommunityService communityService;

    // 게시글 등록
    @Override
    public void registerArticle(ArticleRegisterPostReq articleInfo, Community community, User user) {

        try {
            Article article = Article.builder()
                    .user(user)
                    .community(community)
                    .title(articleInfo.getTitle())
                    .content(articleInfo.getContent())
                    .hits(0)
                    .build();
            articleRepository.save(article);
        } catch (Exception e) {
            throw e;
        }
    }

    // 팬 커뮤니티 안의 모든 게시글 조회
    @Override
    public List<ArticleListGetRes> getAllArticles(Long communityId) {

        List<Article> articles = articleRepository.findAllByCommunityIdOrderByIdDesc(communityId);
        List<ArticleListGetRes> articleListGetRes = new ArrayList<>();

        for (Article article : articles) {
            ArticleListGetRes articleRes = new ArticleListGetRes();
            articleRes.setArticleId(article.getId());
            articleRes.setTitle(article.getTitle());
            LocalDateTime localDateTime = article.getCreatedAt();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            articleRes.setCreatedAt(formattedString);
            articleRes.setHits(article.getHits());
            articleRes.setEmail(article.getUser().getEmail());
            articleListGetRes.add(articleRes);
        }
        return articleListGetRes;
    }

    // 게시글 상세 조회
    @Override
    public ArticleDetailGetRes getArticleDetail(Long articleId, Long communityId) {

        Article article = articleRepository.findByIdAndCommunityId(articleId, communityId).get();

        // 조회수 1 증가
        article.raiseHits(article.getHits() + 1);
        articleRepository.save(article);

        ArticleDetailGetRes articleDetailGetRes = new ArticleDetailGetRes();
        articleDetailGetRes.setArticleId(article.getId());
        articleDetailGetRes.setTitle(article.getTitle());
        articleDetailGetRes.setContent(article.getContent());
        LocalDateTime localDateTime = article.getCreatedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = localDateTime.format(formatter);
        articleDetailGetRes.setCreatedAt(formattedString);
        articleDetailGetRes.setHits(article.getHits());
        articleDetailGetRes.setEmail(article.getUser().getEmail());

        List<CommentDetailGetRes> commentDetailGetResList = new ArrayList<>();
        // 게시글을 댓글 목록
        List<Comment> comments = article.getCommentList();
        for (Comment comment : comments) {
            CommentDetailGetRes commentDetailGetRes = new CommentDetailGetRes();
            commentDetailGetRes.setCommentId(comment.getId());
            commentDetailGetRes.setContent(comment.getContent());
            LocalDateTime localDateTimeComment = comment.getCreatedAt();
            DateTimeFormatter formatterComment = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedStringComment = localDateTimeComment.format(formatterComment);
            commentDetailGetRes.setCreatedAt(formattedStringComment);
            commentDetailGetRes.setEmail(comment.getUser().getEmail());
            commentDetailGetResList.add(commentDetailGetRes);
        }
        articleDetailGetRes.setCommentList(commentDetailGetResList);

        return articleDetailGetRes;
    }

    // 게시글 검색
    public List<ArticleListGetRes> searchArticle(Long communityId, String searchWord){

        QArticle article = QArticle.article;
        Community community = communityService.findById(communityId);
        List<Article> articles = jpaQueryFactory.selectFrom(article)
                .where(article.community.eq(community).and(article.title.contains(searchWord).or(article.content.contains(searchWord))))
                .fetch();
        List<ArticleListGetRes> articleListGetRes = new ArrayList<>();

        for (Article searchedArticle : articles) {
            ArticleListGetRes articleRes = new ArticleListGetRes();
            articleRes.setArticleId(searchedArticle.getId());
            articleRes.setTitle(searchedArticle.getTitle());
            LocalDateTime localDateTime = searchedArticle.getCreatedAt();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            articleRes.setCreatedAt(formattedString);
            articleRes.setHits(searchedArticle.getHits());
            articleRes.setEmail(searchedArticle.getUser().getEmail());
            articleListGetRes.add(articleRes);
        }
        Collections.reverse(articleListGetRes);
        return articleListGetRes;
    }

    // 게시글 수정
    @Override
    public Article modifyArticle(ArticleRegisterPostReq articleInfo, Long articleId, Long communityId) {

        try {
            Article article = articleRepository.findByIdAndCommunityId(articleId, communityId).get();
            article.changeTitleAndContent(articleInfo.getTitle(), articleInfo.getContent());
            articleRepository.save(article);
            return article;
        } catch (Exception e) {
            throw e;
        }
    }

    // 게시글 삭제
    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }

    // 팬 커뮤니티 ID를 기반으로 탐색
    @Override
    public Article findById(Long id) {

        try {
            Article article = articleRepository.findById(id).get();
            return article;
        } catch (Exception e){
            throw e;
        }
    }
}
