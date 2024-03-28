package com.hdpt.device.domain.Wechat;


import lombok.Data;

@Data
public class WxCodeVO {
    private String preAuthCode;
    private String code;
    private String componentAppid;    
    private String componentAccessToken;
    private String componentVerifyTicket;
    private String authorizationCode;
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
