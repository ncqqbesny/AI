package com.soft.mapp.basecenter.controller;

import com.soft.mapp.basecenter.domain.Wechat.SubscribeVo;
import com.soft.mapp.basecenter.domain.Wechat.TemplateMsgVo;
import com.soft.mapp.basecenter.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IWechatController extends IRestService {
    //微信小程序登录
    ServerResponse<?>  getWxOnLogin(String code);
    //微信小程序手机快捷登录
    ServerResponse<?>  wxPhoneLogin(Map params);
    //获得二维码
    ServerResponse<?>  getWxSmallQRCode(String path, Integer scene, Integer width, HttpServletRequest requestNew);
    //微信上传信息,头像
    public ServerResponse<?> wxSubject(HttpServletRequest request,
                                       @RequestParam("file") MultipartFile files);
    //微信小程序发送订阅消息
    public ServerResponse<?> wxSendSubscribeInfo(SubscribeVo vo);
    //微信公众号发送订阅消息
    public ServerResponse<?> wxPublicSendSubscribeInfo(TemplateMsgVo vo);
    //同步公众号用户信息
    public ServerResponse<?> SyncWxPublicUser();
}
