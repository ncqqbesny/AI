package com.hdpt.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdpt.device.dao.IDeviceDao;
import com.hdpt.device.dao.IDeviceExtendDao;
import com.hdpt.device.dao.IUserDao;
import com.hdpt.device.domain.Device.*;
import com.hdpt.device.domain.Wechat.WxSmallAuthorizationVO;
import com.hdpt.device.domain.loginVo.UserDTO;
import com.hdpt.device.domain.message.MessageRecordModel;
import com.hdpt.device.services.*;
import com.hdpt.device.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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
        WxSmallAuthorizationVO wxSmallAuthorizationVO=WeChatUtil.getJscode2session(appId,secret,code);

        resultMap.put("wxAuthorizationInfo",wxSmallAuthorizationVO);
        return null;
    }
}
