package com.ssafy.api.controller;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.db.entity.User;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

        if(userRepository.findByUsername(registerInfo.getId()) != null)  // 같은 id의 회원이 이미 존재하는 경우
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "already exist"));

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        User user = userService.createUser(registerInfo);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/me")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {

        UserRes res = new UserRes();
        try {
            SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
            User user = userDetails.getUser();

            res.setId(user.getId());
            res.setName(user.getName());
            res.setEmail(user.getEmail());
        } catch (Exception e) {

            res.setId(0L);
            return ResponseEntity.status(200).body(res);
        }

        return ResponseEntity.status(200).body(res);
    }

    @PutMapping(value = "/{username}")
    @ApiOperation(value = "회원 본인 정보 삭제", notes = "로그인한 회원 본인의 정보를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> remove(@PathVariable("username") String username, @ApiIgnore Authentication authentication) {

        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String username_sub = userDetails.getUsername();

        if (username.equals(username_sub)) { // 현재 로그인된 유저와 삭제하려는 유저가 같으면 service 실행

            userService.removeUser(username);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }
        return ResponseEntity.status(401).body(BaseResponseBody.of(401,"fail"));
    }
}
