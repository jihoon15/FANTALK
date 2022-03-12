package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;


/**
 * 게시글 등록 DTO 정의
 */
@Getter
@Setter
@ApiModel("ArticleRegisterPostRequest")
public class ArticleRegisterPostReq {

    @ApiModelProperty(name="게시글 제목", example="제목1")
    String title;

    @ApiModelProperty(name="게시글 내용", example="내용1")
    @Column(length=200000000)
    String content;
}
