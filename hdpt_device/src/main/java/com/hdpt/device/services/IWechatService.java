package com.hdpt.device.services;


import java.util.Map;

public interface IWechatService {
    //根据微信小程序登录code获得信息
    Map<String,Object>  getWxSmallPrgLoginInfo(String code);

}
