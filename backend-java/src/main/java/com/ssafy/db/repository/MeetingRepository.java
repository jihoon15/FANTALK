package com.ssafy.db.repository;

import com.ssafy.db.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * 팬미팅 레포지토리 정의
 */
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByTitleContainingOrderByOpenDateAsc(String searchWord);
    Optional<Meeting> findById(Long id);
    List<Meeting> findByUserId(Long userId);
    Optional<Meeting> findByIdAndUserId(Long id, Long userId);
}
