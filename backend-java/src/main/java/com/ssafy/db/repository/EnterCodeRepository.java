package com.ssafy.db.repository;


import com.ssafy.db.entity.EnterCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 입장코드 레포지토리 정의
 */
@Repository
public interface EnterCodeRepository extends JpaRepository<EnterCode, String> {

    List<EnterCode> findByMeetingId(Long meetingId);
}
