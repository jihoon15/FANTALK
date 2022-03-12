package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


/**
 * 댓글 등록 DTO 정의
 */
@Getter
@Setter
@ApiModel("CommentRegisterPostRequest")
public class CommentRegisterPostReq {

    @ApiModelProperty(name="댓글 내용", example="내용1")
    @Column(length=200000000)
    private String content;
}
