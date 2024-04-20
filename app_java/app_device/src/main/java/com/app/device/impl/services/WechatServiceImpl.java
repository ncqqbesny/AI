package com.app.device.impl.services;

import com.app.device.utils.WeChatUtil;
import com.app.device.dao.IUserDao;
import com.app.device.domain.Device.*;
import com.app.device.domain.Wechat.WxSmallAuthorizationVO;
import com.app.device.services.*;
import com.app.device.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WechatServiceImpl implements IWechatService {
    private final static Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);
    @Autowired
    IUserDao userDao;


    @Override
    public Map<String, Object> getWxSmallPrgLoginInfo(String code) {
        Map<String, Object> resultMap=new HashMap<>();
        String appId="";
        String secret="";
        WxSmallAuthorizationVO wxSmallAuthorizationVO= WeChatUtil.getJscode2session(appId,secret,code);

        resultMap.put("wxAuthorizationInfo",wxSmallAuthorizationVO);
        return null;
    }
}
