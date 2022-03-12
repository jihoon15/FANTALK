package com.ssafy.api.service;

import com.ssafy.api.request.EnterCodeEnterPutReq;
import com.ssafy.api.request.MeetingRegisterPostReq;
import com.ssafy.api.response.MeetingDetailGetRes;
import com.ssafy.api.response.MyMeetingDetailGetRes;
import com.ssafy.db.entity.EnterCode;
import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.EnterCodeRepository;
import com.ssafy.db.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * 팬미팅 서비스 구현체 정의
 */
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;
    private final EnterCodeRepository enterCodeRepository;

    // 팬미팅 등록
    @Override
    public void registerMeeting(MeetingRegisterPostReq meetingInfo, User user) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(meetingInfo.getOpenDate(), formatter);

        try {
            Meeting meeting = Meeting.builder()
                    .title(meetingInfo.getTitle())
                    .maxUser(meetingInfo.getMaxUser())
                    .openDate(dateTime)
                    .isInManager(false)
                    .isActive(false)
                    .user(user)
                    .build();
            meetingRepository.save(meeting);

            // 해당 팬미팅의 입장 코드 생성
            for (int i = 0; i < meeting.getMaxUser() + 1; i++) {
                String ranDomCode = RandomStringUtils.random(12, true, true);
                EnterCode enterCode = EnterCode.builder()
                        .id(ranDomCode)
                        .meeting(meeting)
                        .checked(false)
                        .build();
                enterCodeRepository.save(enterCode);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    // 모든 팬미팅 목록 조회
    @Override
    public List<MeetingDetailGetRes> getAllMeeting() {

        List<Meeting> findMeetings = meetingRepository.findAll(Sort.by(Sort.Direction.ASC, "openDate"));
        List<MeetingDetailGetRes> meetings = new ArrayList<>();
        for (Meeting findMeeting : findMeetings) {
            MeetingDetailGetRes meeting = new MeetingDetailGetRes();
            meeting.setId(findMeeting.getId());
            meeting.setTitle(findMeeting.getTitle());
            LocalDateTime localDateTime = findMeeting.getOpenDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            meeting.setOpenDate(formattedString);
            meetings.add(meeting);
        }
        return meetings;
    }

    // 팬미팅 상세 조회
    @Override
    public MeetingDetailGetRes getMeetingDetail(Long meetingId) {

        try {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            MeetingDetailGetRes meetingDetailGetRes = new MeetingDetailGetRes();
            meetingDetailGetRes.setId(meeting.getId());
            meetingDetailGetRes.setTitle(meeting.getTitle());
            LocalDateTime localDateTime = meeting.getOpenDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            meetingDetailGetRes.setOpenDate(formattedString);
            return meetingDetailGetRes;
        } catch (Exception e) {
            throw e;
        }
    }

    // 팬미팅 제목을 기반으로 검색
    @Override
    public List<MeetingDetailGetRes> searchMeeting(String searchWord) {

        List<Meeting> findMeetings = meetingRepository.findByTitleContainingOrderByOpenDateAsc(searchWord);
        List<MeetingDetailGetRes> meetings = new ArrayList<>();
        for (Meeting findMeeting : findMeetings) {
            MeetingDetailGetRes meeting = new MeetingDetailGetRes();
            meeting.setId(findMeeting.getId());
            meeting.setTitle(findMeeting.getTitle());
            LocalDateTime localDateTime = findMeeting.getOpenDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            meeting.setOpenDate(formattedString);
            meetings.add(meeting);
        }
        return meetings;
    }

    // 팬미팅 수정
    @Override
    public Meeting modifyMeeting(MeetingRegisterPostReq meetingInfo, Long meetingId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(meetingInfo.getOpenDate(), formatter);

        try {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            meeting.changeTitleAndOpenDateAndMaxUser(meetingInfo.getTitle(), dateTime, meetingInfo.getMaxUser());
            meetingRepository.save(meeting);
            return meeting;
        } catch (Exception e) {
            throw e;
        }
    }

    // 팬미팅 삭제
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingRepository.delete(meeting);
    }

    // 신청한 팬미팅 목록 조회
    @Override
    public List<MyMeetingDetailGetRes> getAllMyMeeting(Long userId) {

        List<Meeting> findMyMeetings = meetingRepository.findByUserId(userId);
        List<MyMeetingDetailGetRes> myMeetingDetailGetResList = new ArrayList<>();
        for (Meeting findMyMeeting : findMyMeetings) {
            MyMeetingDetailGetRes myMeetingDetailGetRes = new MyMeetingDetailGetRes();
            myMeetingDetailGetRes.setId(findMyMeeting.getId());
            myMeetingDetailGetRes.setTitle(findMyMeeting.getTitle());
            LocalDateTime localDateTime = findMyMeeting.getOpenDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            myMeetingDetailGetRes.setOpenDate(formattedString);
            myMeetingDetailGetRes.setMaxUser(findMyMeeting.getMaxUser());
            myMeetingDetailGetResList.add(myMeetingDetailGetRes);
        }
        return myMeetingDetailGetResList;
    }

    // 신청한 팬미팅 상세 조회
    @Override
    public MyMeetingDetailGetRes getMyMeetingDetail(Long meetingId, Long userId) {

        try {
            Meeting findMeeting = meetingRepository.findByIdAndUserId(meetingId, userId).get();
            MyMeetingDetailGetRes myMeetingDetailGetRes = new MyMeetingDetailGetRes();
            myMeetingDetailGetRes.setId(findMeeting.getId());
            myMeetingDetailGetRes.setTitle(findMeeting.getTitle());
            LocalDateTime localDateTime = findMeeting.getOpenDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedString = localDateTime.format(formatter);
            myMeetingDetailGetRes.setOpenDate(formattedString);
            myMeetingDetailGetRes.setMaxUser(findMeeting.getMaxUser());
            return myMeetingDetailGetRes;
        } catch (Exception e) {
            throw e;
        }
    }

    // 기업회원 팬미팅 입장
    @Override
    public String enterMeetingManager(EnterCodeEnterPutReq enterCodeInfo, User user) {

        try {
            List<Meeting> meetings = meetingRepository.findByUserId(user.getId());
            for (Meeting meeting : meetings) {
                List<EnterCode> enterCodes = enterCodeRepository.findByMeetingId(meeting.getId());
                for (EnterCode enterCode : enterCodes) {
                    if (enterCode.getId().equals(enterCodeInfo.getEnterCode())) {
                        if (!meeting.isInManager()) {
                            meeting.changeIsInManager();
                            meetingRepository.save(meeting);
                            return "SUCCESS";
                        }
                    }
                }
            }
            return "FAIL";
        } catch (Exception e) {
            throw e;
        }
    }

    // 일반회원 팬미팅 입장
    @Override
    public String enterMeetingUser(EnterCodeEnterPutReq enterCodeInfo, Long meetingId) {

        try {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            if (meeting.isInManager()) {
                if (meeting.isActive()) {
                    return "MEETING ING";
                } else {
                    List<EnterCode> enterCodes = enterCodeRepository.findByMeetingId(meetingId);
                    for (EnterCode enterCode : enterCodes) {
                        if (enterCode.getId().equals(enterCodeInfo.getEnterCode())) {
                            if (enterCode.isChecked()) {
                                return "NO ENTER TWICE";
                            } else {
                                enterCode.changeChecked();
                                enterCodeRepository.save(enterCode);
                                meeting.changeIsActive();
                                meetingRepository.save(meeting);
                                return "SUCCESS";
                            }
                        }
                    }
                    return "Wrong EnterCode";
                }
            } else {
                return "MANAGER NOT IN";
            }
            } catch (Exception e) {
                throw e;
            }
    }

    // 기업회원의 팬미팅 퇴장
    @Override
    public void exitMeetingManager(Long meetingId) {

        Meeting meeting = meetingRepository.findById(meetingId).get();
        if (meeting.isInManager()) {
            meeting.changeIsInManager();
            meetingRepository.save(meeting);
        }
    }

    // 일반회원의 팬미팅 입장
    @Override
    public void exitMeetingUser(Long meetingId) {

        Meeting meeting = meetingRepository.findById(meetingId).get();
        meeting.changeIsActive();
        meetingRepository.save(meeting);
    }
}
