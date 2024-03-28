package com.soft.mapp.basecenter.domain.Wechat;

import lombok.Data;

import java.util.List;

@Data
public class WxUserInfoVO {
 
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
    private String unionid;
 
 
}
