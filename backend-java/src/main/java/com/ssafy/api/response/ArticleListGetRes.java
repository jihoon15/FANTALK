package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 게시글 목록 반환 DTO 정의
 */
@Getter
@Setter
@ApiModel("ArticleListGetResponse")
public class ArticleListGetRes {

    @ApiModelProperty(name="게시글 ID", example="1")
    Long articleId;

    @ApiModelProperty(name="게시글 제목", example="제목1")
    String title;

    @ApiModelProperty(name="등록일자", example="2022-01-01 00:00")
    String createdAt;

    @ApiModelProperty(name="조회수", example="1")
    int hits;

    @ApiModelProperty(name="작성자 이메일", example="xxxxxx@xxx.com")
    String email;
}
