package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegisterPostReq;
import com.ssafy.db.entity.Community;
import com.ssafy.db.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 팬 커뮤니티 서비스 구현체 정의
 */
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;

    // 팬 커뮤니티 등록
    @Override
    public void registerCommunity(CommunityRegisterPostReq communityInfo){

        try {
            Community community = Community.builder()
                    .name(communityInfo.getName())
                    .title(communityInfo.getTitle())
                    .build();
            communityRepository.save(community);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 같은 name의 팬 커뮤니티가 이미 존재하는지 확인
    @Override
    public boolean findByName(String name) {

        if (communityRepository.findByName(name).isPresent()) {
            return true;
        }
        return false;
    }

    // 모든 팬 커뮤니티 목록 조회
    @Override
    public List<Community> getAllCommunity() {

        List<Community> communities = communityRepository.findAll();
        return communities;
    }

    // 팬 커뮤니티 이름을 기반으로 검색
    @Override
    public List<Community> searchCommunity(String searchWord) {

        List<Community> communities = communityRepository.findByNameContaining(searchWord);
        return communities;
    }

    // 팬 커뮤니티 ID를 기반으로 탐색
    @Override
    public Community findById(Long id) {

        try {
            Community community = communityRepository.findById(id).get();
            return community;
        } catch (Exception e){
            throw e;
        }
    }
}
