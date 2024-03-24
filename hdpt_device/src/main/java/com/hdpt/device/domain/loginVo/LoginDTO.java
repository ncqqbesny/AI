package com.hdpt.device.domain.loginVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 登录传输类
 */
@Data
public class LoginDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;


}
