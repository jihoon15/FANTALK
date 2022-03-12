package com.ssafy.api.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 기업회원 팬미팅 상세 반환 DTO 정의
 */
@Getter
@Setter
@ApiModel("MyMeetingDetailGetResponse")
public class MyMeetingDetailGetRes {

    @ApiModelProperty(name="미팅 id", example="1")
    private Long id;

    @ApiModelProperty(name="미팅 제목", example="제목1")
    private String title;

    @ApiModelProperty(name="미팅 오픈일자", example="2022-02-11 14:00")
    private String openDate;

    @ApiModelProperty(name="제한 인원", example="100")
    private int maxUser;
}
