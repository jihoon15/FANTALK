package com.ssafy.db.repository;


import com.ssafy.db.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * 팬 커뮤니티 레포지토리 정의
 */
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findByNameContaining(String searchWord);
    Optional<Community> findByName(String name);
    Optional<Community> findById(Long id);
}
