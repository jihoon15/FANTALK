package com.ssafy.db.repository;

import com.ssafy.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * 게시글 레포지토리 정의
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByCommunityIdOrderByIdDesc(Long communityId);
    Optional<Article> findByIdAndCommunityId(Long id, Long communityId);
    Optional<Article> findById(Long id);
}
