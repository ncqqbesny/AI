package com.app.device.domain.loginVo;

import lombok.Data;

import java.util.Date;

@Data
public class SysToken {
    private Integer userId;
    private String token;
    private Date expireTime;
    private Date updateTime;
}
