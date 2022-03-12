package com.ssafy.api.service;


import com.ssafy.api.request.EnterCodeEnterPutReq;
import com.ssafy.api.request.MeetingRegisterPostReq;
import com.ssafy.api.response.MeetingDetailGetRes;
import com.ssafy.api.response.MyMeetingDetailGetRes;
import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.User;

import java.util.List;


/**
 * 팬미팅 서비스 인터페이스 정의
 */
public interface MeetingService {

    void registerMeeting(MeetingRegisterPostReq meetingInfo, User user);
    List<MeetingDetailGetRes> getAllMeeting();
    MeetingDetailGetRes getMeetingDetail(Long meetingId);
    List<MeetingDetailGetRes> searchMeeting(String searchWord);
    Meeting modifyMeeting(MeetingRegisterPostReq meetingInfo, Long meetingId);
    void deleteMeeting(Meeting meeting);
    List<MyMeetingDetailGetRes> getAllMyMeeting(Long userId);
    MyMeetingDetailGetRes getMyMeetingDetail(Long meetingId, Long userId);
    String enterMeetingManager(EnterCodeEnterPutReq enterCodeInfo, User user);
    String enterMeetingUser(EnterCodeEnterPutReq enterCodeInfo, Long meetingId);
    void exitMeetingManager(Long meetingId);
    void exitMeetingUser(Long meetingId);
}
