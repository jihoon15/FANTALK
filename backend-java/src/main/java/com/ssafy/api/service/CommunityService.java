package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegisterPostReq;
import com.ssafy.db.entity.Community;

import java.util.List;


/**
 * 팬 커뮤니티 서비스 인터페이스 정의
 */
public interface CommunityService {

    void registerCommunity(CommunityRegisterPostReq communityInfo);
    boolean findByName(String name);
    List<Community> getAllCommunity();
    List<Community> searchCommunity(String searchWord);
    Community findById(Long id);
}
