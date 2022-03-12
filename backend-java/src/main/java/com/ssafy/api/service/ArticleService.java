package com.ssafy.api.service;

import com.ssafy.api.request.ArticleRegisterPostReq;
import com.ssafy.api.response.ArticleDetailGetRes;
import com.ssafy.api.response.ArticleListGetRes;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;

import java.util.List;


/**
 * 게시글 서비스 인터페이스 정의
 */
public interface ArticleService {

    void registerArticle(ArticleRegisterPostReq articleInfo, Community community, User user);
    List<ArticleListGetRes> getAllArticles(Long communityId);
    ArticleDetailGetRes getArticleDetail(Long communityId, Long articleId);
    List<ArticleListGetRes> searchArticle(Long community_id, String searchWord);
    Article modifyArticle(ArticleRegisterPostReq articleInfo, Long articleId, Long communityId);
    void deleteArticle(Article article);
    Article findById(Long id);
}
