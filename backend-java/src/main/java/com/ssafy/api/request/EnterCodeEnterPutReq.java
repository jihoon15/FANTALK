package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 입장코드 입장 DTO 정의
 */
@Getter
@Setter
@ApiModel("EnterCodeEnterPutRequest")
public class EnterCodeEnterPutReq {

    @ApiModelProperty(name="입장코드", example="6vnz5zSqsQTe")
    private String enterCode;
}
