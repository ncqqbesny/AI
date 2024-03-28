package com.soft.mapp.basecenter.domain.loginVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录传输类
 */
@Data
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;


}
