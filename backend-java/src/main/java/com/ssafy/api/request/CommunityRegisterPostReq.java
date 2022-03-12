package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 팬 커뮤니티 등록 DTO 정의
 */
@Getter
@Setter
@ApiModel("CommunityRegisterPostRequest")
public class CommunityRegisterPostReq {

    @ApiModelProperty(name="연예인 이름", example="아이유")
    String name;

    @ApiModelProperty(name="팬 커뮤니티 이름", example="어른말고아이유")
    String title;
}
