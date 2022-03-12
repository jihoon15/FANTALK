package com.ssafy.api.controller;

import com.ssafy.api.request.EnterCodeEnterPutReq;
import com.ssafy.api.request.MeetingRegisterPostReq;
import com.ssafy.api.response.MeetingDetailGetRes;
import com.ssafy.api.response.MyMeetingDetailGetRes;
import com.ssafy.api.service.MeetingService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.Meeting;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.MeetingRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 팬미팅 컨트롤러 정의
 */
@Api(value = "팬미팅 API", tags = {"Meeting"})
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingRepository meetingRepository;

    // 팬미팅 생성
    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 204, message = "실패(FAIL)"),
            @ApiResponse(code = 400, message = "관리자 인증 실패(NO MANAGER)"),
    })
    @ApiOperation(value = "팬미팅 등록", notes = "팬미팅을 등록")
    public ResponseEntity<String> registerMeeting(
            @RequestBody @ApiParam(value="팬미팅 정보", required = true) MeetingRegisterPostReq meetingInfo,
            @ApiIgnore Authentication authentication) {
        logger.info("registerMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 관리자 여부 확인
        if (user.getRole().equals("ROLE_MANAGER")) {
            try {
                meetingService.registerMeeting(meetingInfo, user);
                return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<String>("NO MANAGER", HttpStatus.BAD_REQUEST);
        }
    }

    // 모든 팬미팅 목록 조회
    @GetMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "팬미팅 목록 리스트 반환, 없을 시 [] 반환"),
    })
    @ApiOperation(value = "모든 팬미팅 목록 조회", notes = "모든 팬미팅 목록을 조회")
    public ResponseEntity<List<MeetingDetailGetRes>> getAllMeeting() {
        logger.info("getAllMeeting 호출");

        return new ResponseEntity<List<MeetingDetailGetRes>>(meetingService.getAllMeeting(), HttpStatus.OK);
    }

    // 팬미팅 상세 조회
    @GetMapping("/{meetingId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 400, message = "팬미팅 조회 오류(FAIL)")
    })
    @ApiOperation(value = "팬미팅 상세 조회", notes = "팬미팅을 상세 조회")
    public ResponseEntity<MeetingDetailGetRes> getMeetingDetail(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId) {
        logger.info("exitMeeting 호출");

        try {
            return new ResponseEntity<MeetingDetailGetRes>(meetingService.getMeetingDetail(meetingId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<MeetingDetailGetRes>(meetingService.getMeetingDetail(meetingId), HttpStatus.BAD_REQUEST);
        }

    }

    // 팬미팅 제목을 기반으로 검색
    @GetMapping("/search")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 반환, 없을 시 [] 반환"),
    })
    @ApiOperation(value = "팬미팅 검색", notes = "팬미팅 제목을 기반으로 검색")
    public ResponseEntity<List<MeetingDetailGetRes>> searchMeeting(@RequestParam("q") @ApiParam(value="검색어", required = true) String searchWord) {
        logger.info("searchMeeting 호출");

        return new ResponseEntity<List<MeetingDetailGetRes>>(meetingService.searchMeeting(searchWord), HttpStatus.OK);
    }

    // 팬미팅 수정
    @PutMapping("/{meetingId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 204, message = "실패(FAIL)"),
            @ApiResponse(code = 400, message = "관리자 인증 실패(NO MANAGER)"),
    })
    @ApiOperation(value = "팬미팅 수정", notes = "팬미팅을 수정")
    public ResponseEntity<String> modifyMeeting(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId,
            @RequestBody @ApiParam(value="팬미팅 정보", required = true) MeetingRegisterPostReq meetingInfo,
            @ApiIgnore Authentication authentication) {
        logger.info("modifyMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 관리자 여부 확인
        if (user.getRole().equals("ROLE_MANAGER")) {
            try {
                Meeting meeting = meetingRepository.findById(meetingId).get();
                // 현재 로그인된 회원 id와 수정 요청한 팬미팅의 회원 id가 일치하는지 확인
                if (meeting.getUser().getId().equals(user.getId())) {
                    meetingService.modifyMeeting(meetingInfo, meetingId);
                    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("NO MANAGER", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e){
                return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<String>("NO MANAGER", HttpStatus.BAD_REQUEST);
        }
    }

    // 팬미팅 삭제
    @DeleteMapping("/{meetingId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 204, message = "실패(FAIL)"),
            @ApiResponse(code = 400, message = "관리자 인증 실패(NO MANAGER)"),
    })
    @ApiOperation(value = "팬미팅 삭제", notes = "팬미팅을 삭제")
    public ResponseEntity<String> deleteMeeting(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId,
            @ApiIgnore Authentication authentication) {
        logger.info("deleteMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 관리자 여부 확인
        if (user.getRole().equals("ROLE_MANAGER")) {
            try {
                Meeting meeting = meetingRepository.findById(meetingId).get();
                // 현재 로그인된 회원 id와 삭제 요청한 팬미팅의 회원 id가 일치하는지 확인
                if (meeting.getUser().getId().equals(user.getId())) {
                    meetingService.deleteMeeting(meeting);
                    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("NO MANAGER", HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<String>("NO MANAGER", HttpStatus.BAD_REQUEST);
        }
    }

    // 기업회원 확인
    @GetMapping("/isManager")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 400, message = "실패(Fail)"),
    })
    @ApiOperation(value = "기업회원 확인", notes = "기업회원 여부 확인")
    public ResponseEntity<String> checkManager(@ApiIgnore Authentication authentication) {
        logger.info("checkManager 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 관리자 여부 확인
        if (user.getRole().equals("ROLE_MANAGER")) {
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 신청한 팬미팅 목록 조회
    @GetMapping("/me")
    @ApiResponses({
            @ApiResponse(code = 200, message = "팬미팅 목록 리스트 반환, 없을 시 [] 반환"),
    })
    @ApiOperation(value = "신청한 팬미팅 목록 조회", notes = "기업회원이 신청한 팬미팅 전체 목록 조회")
    public ResponseEntity<List<MyMeetingDetailGetRes>> getAllMyMeeting(@ApiIgnore Authentication authentication) {
        logger.info("getAllMyMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        return new ResponseEntity<List<MyMeetingDetailGetRes>>(meetingService.getAllMyMeeting(user.getId()), HttpStatus.OK);
    }

    // 신청한 팬미팅 상세 조회
    @GetMapping("/me/{meetingId}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS(성공)"),
            @ApiResponse(code = 400, message = "FAIL(실패)"),
    })
    @ApiOperation(value = "신청한 팬미팅 상세 조회", notes = "기업회원이 신청한 특정 팬미팅 상세 조회")
    public ResponseEntity<?> getMyMeetingDetail(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId,
            @ApiIgnore Authentication authentication) {
        logger.info("getMyMeetingDetail 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        try {
            MyMeetingDetailGetRes myMeetingDetailGetRes = meetingService.getMyMeetingDetail(meetingId, user.getId());
            return new ResponseEntity<MyMeetingDetailGetRes>(myMeetingDetailGetRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 팬미팅 입장
    @PutMapping("/{meetingId}/enter")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS(성공)"),
            @ApiResponse(code = 403, message = "NO ENTER TWICE(중복 입장 불가능)"),
            @ApiResponse(code = 401, message = "MANAGER NOT IN(기업회원 입장 전 일반회원 입장 불가능"),
            @ApiResponse(code = 406, message = "MEETING ING(일대일 미팅 진행중"),
            @ApiResponse(code = 400, message = "Wrong EnterCode(잘못된 입장 코드")
    })
    @ApiOperation(value = "팬미팅 입장", notes = "기업회원과 일반회원의 팬미팅에 입장")
    public ResponseEntity<String> enterMeeting(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId,
            @RequestBody @ApiParam(value="입장 코드", required = true) EnterCodeEnterPutReq enterCodeInfo,
            @ApiIgnore Authentication authentication) {
        logger.info("enterMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 기업회원의 팬미팅 입장
        if (user.getRole().equals("ROLE_MANAGER")) {
            try {
                String result = meetingService.enterMeetingManager(enterCodeInfo, user);
                if (result.equals("SUCCESS")) {
                    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("Wrong EnterCode", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<String>("Wrong EnterCode", HttpStatus.BAD_REQUEST);
            }
        // 일반회원의 팬미팅 입장
        } else {
            try {
                String result = meetingService.enterMeetingUser(enterCodeInfo, meetingId);
                if (result.equals("SUCCESS")) {
                    return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
                } else if (result.equals("MEETING ING")) {
                    return new ResponseEntity<String>("MEETING ING", HttpStatus.NOT_ACCEPTABLE);
                } else if (result.equals(("NO ENTER TWICE"))) {
                    return new ResponseEntity<String>("NO ENTER TWICE", HttpStatus.FORBIDDEN);
                } else if (result.equals("MANAGER NOT IN")) {
                    return new ResponseEntity<String>("MANAGER NOT IN", HttpStatus.UNAUTHORIZED);
                } else {
                    return new ResponseEntity<String>("Wrong EnterCode", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println(e);
                return new ResponseEntity<String>("Wrong EnterCode", HttpStatus.BAD_REQUEST);
            }
        }
    }

    // 팬미팅 퇴장
    @PutMapping("/{meetingId}/exit")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SUCCESS(성공)"),
    })
    @ApiOperation(value = "팬미팅 퇴장", notes = "기업회원과 일반회원의 팬미팅 퇴장")
    public ResponseEntity<String> exitMeeting(
            @PathVariable("meetingId") @ApiParam(value="팬미팅 id", required = true) Long meetingId,
            @ApiIgnore Authentication authentication) {
        logger.info("exitMeeting 호출");

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUsername(userDetails.getUsername());

        // 기업회원의 팬미팅 퇴장
        if (user.getRole().equals("ROLE_MANAGER")) {
            meetingService.exitMeetingManager(meetingId);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        // 일반회원의 팬미팅 퇴장
        } else {
            meetingService.exitMeetingUser(meetingId);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        }
    }
}
