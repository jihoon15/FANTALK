package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 팬미팅 등록 DTO 정의
 */
@Getter
@Setter
@ApiModel("MeetingRegisterPostRequest")
public class MeetingRegisterPostReq {

    @ApiModelProperty(name="미팅 제목", example="제목1")
    private String title;

    @ApiModelProperty(name="최대 인원", example="100")
    private int maxUser;

    @ApiModelProperty(name="미팅 오픈일자", example="2022-02-11 14:00")
    private String openDate;
}
