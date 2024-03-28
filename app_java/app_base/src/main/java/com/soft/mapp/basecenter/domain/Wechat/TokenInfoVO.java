package com.soft.mapp.basecenter.domain.Wechat;

import lombok.Data;

@Data
public class TokenInfoVO {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}

