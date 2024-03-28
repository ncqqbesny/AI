package com.soft.mapp.basecenter.domain.Wechat;


import lombok.Data;

@Data
public class WxCodeVO {
    private String preAuthCode;
    private String code;
    private String componentAppid;    
    private String componentAccessToken;
    private String componentVerifyTicket;
    private String authorizationCode;
    //第一个拉取的OPENID，不填默认从头开始拉取
    private String nextOpenId;
    /**
     * 授权APPID
     */
    private String authorizerAppid;    
    /**
     * 授权刷新
     */
    private String authorizerRefreshToken;
    /**
     * 获得授
     */
    private String authorizerAccessToken;
   
}
