package com.soft.mapp.basecenter.domain.Wechat;

import lombok.Data;

@Data
public class WxSmallAuthorizationVO {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}

