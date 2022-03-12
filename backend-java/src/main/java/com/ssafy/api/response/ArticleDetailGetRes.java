package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;


/**
 * 게시글 상세 반환 DTO 정의
 */
@Getter
@Setter
@ApiModel("ArticleDetailGetResponse")
public class ArticleDetailGetRes {

    @ApiModelProperty(name="게시글 ID", example="1")
    Long articleId;

    @ApiModelProperty(name="게시글 제목", example="제목1")
    String title;

    @ApiModelProperty(name="게시글 내용", example="내용1")
    @Column(length=200000000)
    String content;

    @ApiModelProperty(name="등록일자", example="2022-01-01 00:00")
    String createdAt;

    @ApiModelProperty(name="조회수", example="1")
    int hits;

    @ApiModelProperty(name="작성자 이메일", example="xxxxxx@xxx.com")
    String email;

    @ApiModelProperty(name="댓글 목록", example="{[content: xxxx, createdAt: xxxx-xx-xx xx:xx ....")
    List<CommentDetailGetRes> commentList = new ArrayList<>();
}
