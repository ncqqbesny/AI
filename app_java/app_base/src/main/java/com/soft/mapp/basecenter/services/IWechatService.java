package com.soft.mapp.basecenter.services;


import com.soft.mapp.basecenter.domain.Wechat.SubscribeVo;
import com.soft.mapp.basecenter.domain.Wechat.TemplateMsgVo;

import java.util.Map;

public interface IWechatService {
    //获得小程序token
    public Map<String, String> getWxSmallAccessTokenByConfig();
    //根据微信小程序登录code获得信息
    Map<String,Object>  getWxSmallPrgLoginInfo(String code);
    //手机号快捷登录,用户名密码登录
    Map<String,Object>  getWxSmallPhoneLoginInfo(String code,String openId,String username,String password);
    //获得微信小程序二维码
    String  getWxSmallQRCode(String path,Integer scene,Integer width);
    //按配置获得普通二维码
    String getWxSmarlQrcCodeUrl(Integer mid);
    //发送小程序订阅消息
    String wxSmarllSendSubscribeInfo(SubscribeVo subscribeVo);

    //获得公众号token
    Map<String, String> getPublicAccessTokenByConfig();
    //发送公众号模板消息
    String wxPublicSendSubscribeInfo(TemplateMsgVo subscribeVo);

    String timerWxPublicSendSubscribeInfo();
    //同步微信公众号用户
    String syncWxPublicUser();
}
