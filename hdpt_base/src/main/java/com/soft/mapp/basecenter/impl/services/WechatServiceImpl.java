package com.soft.mapp.basecenter.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.soft.mapp.basecenter.dao.IUserDao;
import com.soft.mapp.basecenter.dao.IUserInfoDao;
import com.soft.mapp.basecenter.dao.IWxUserDao;
import com.soft.mapp.basecenter.dao.IWxUserSyncTaskDao;
import com.soft.mapp.basecenter.domain.Wechat.*;
import com.soft.mapp.basecenter.domain.loginVo.User;
import com.soft.mapp.basecenter.domain.loginVo.UserInfoDTO;
import com.soft.mapp.basecenter.domain.loginVo.UserQuery;
import com.soft.mapp.basecenter.domain.loginVo.UserVo;
import com.soft.mapp.basecenter.domain.sysParam.SysPramVo;
import com.soft.mapp.basecenter.services.IShiroService;
import com.soft.mapp.basecenter.services.ISysParamService;
import com.soft.mapp.basecenter.services.IUserService;
import com.soft.mapp.basecenter.services.IWechatService;
import com.soft.mapp.basecenter.type.StatusEnum;
import com.soft.mapp.basecenter.type.UserTypeEnum;
import com.soft.mapp.basecenter.type.WxUserTypeEnum;
import com.soft.mapp.basecenter.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
public class WechatServiceImpl implements IWechatService {
    private final static Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);
    @Autowired
    IUserDao userDao;
    @Autowired
    ISysParamService sysParamService;
    @Autowired
    IShiroService shiroService;
    @Autowired
    IUserService userService;
    @Autowired
    IUserInfoDao userInfoDao;
    @Autowired
    IWxUserSyncTaskDao wxUserSyncTaskDao;
    @Autowired
    IWxUserDao wxUserDao;
    @Value("${file.image.path}")
    public String imagePath;
    @Value("${file.logo.path}")
    public String logoImage;
    @Value("${http.base.pre.url}")
    public String httpPreUrl;


    /**
     * 获得带mid参数的普通二维码
     *
     * @param mid
     * @return
     */
    public String getWxSmarlQrcCodeUrl(Integer mid) {
        Map<String, String> configMap = sysParamService.getConfigMapByType("wx_small_config");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = configMap.get("http_page_prefix") + mid;
        Integer hight = ConvertUtils.toInt(configMap.get("hight"));
        Integer width = ConvertUtils.toInt(configMap.get("width"));

        //在图片上生成二维码
        //String roundImage = "G:\\myDoc\\cy\\LOGO\\test.png";
        //String logoImage = "G:\\myDoc\\cy\\LOGO\\linkomlogo.png";
        //String url = "https://hdpt.linkom.com.cn/#/wxSmallPrg?mid=145";
        //String outImgPath = "g:/6.jpg";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String dirName = sf.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + ".jpg";
        String subPathFile = "/" + dirName + "/" + fileName;
        String currentDirectoryPath = System.getProperty("user.dir");
        String outImgPath = currentDirectoryPath + imagePath + "/" + dirName;
        String logoImagePath = currentDirectoryPath + imagePath + logoImage;
        //原图片路径 loge图片 二维码 输出地址
        String msg = AddWatermarkUtil.addWater("", logoImagePath, url, outImgPath, fileName, width, hight, 0, 0);
        if (StringUtil.isEmpty(msg)) {
            String qrCodeImageUrl = httpPreUrl + "/images" + subPathFile;
            return qrCodeImageUrl;
        }
        return "";
    }

    @Override
    public String wxSmarllSendSubscribeInfo(SubscribeVo subscribeVo) {
        Map<String, String> wxConfigMap = getWxSmallAccessTokenByConfig();
        subscribeVo.setAccessToken(wxConfigMap.get("access_token"));
        subscribeVo.setTemplateId("LbJvDvp-GNLR0tuKgIl2noImY9u5dkf5ZNMUJCPcivU");
        if (StringUtils.isNotEmpty(subscribeVo.getTemplateId())) {
            subscribeVo.setTemplateId(subscribeVo.getTemplateId());
        }
        if (StringUtils.isNotEmpty(subscribeVo.getData())) {
            subscribeVo.setData(subscribeVo.getData());
        }
        if (StringUtils.isNotEmpty(subscribeVo.getPage())) {
            subscribeVo.setData(subscribeVo.getPage());
        }
        subscribeVo.setLang("zh_CN");
        if (StringUtils.isNotEmpty(subscribeVo.getLang())) {
            subscribeVo.setLang(subscribeVo.getLang());
        }
        WeChatUtil.subscribeSend(subscribeVo);

        return null;
    }

    @Override
    public String wxPublicSendSubscribeInfo(TemplateMsgVo vo) {
        Map<String, String> confMap = getPublicAccessTokenByConfig();
        if (StringUtils.isEmpty(confMap.get("access_token"))) {
            return "没有查到配置token";
        }
        vo.setAccessToken(confMap.get("access_token"));
        if (StringUtils.isEmpty(confMap.get("subscribe_exception"))) {
            return "没有查到配置模板ID";
        }
        vo.setTemplateId(confMap.get("subscribe_exception"));
        if (StringUtils.isEmpty(confMap.get("pagepath")) && StringUtils.isNotEmpty(confMap.get("url"))) {
            vo.setUrl(confMap.get("url"));
        }
        if (StringUtils.isNotEmpty(confMap.get("pagepath"))) {
            vo.setPagepath(confMap.get("pagepath"));
            Map<String, String> configMap = sysParamService.getConfigMapByType("wx_small_config");
            String appId = configMap.get("app_id");
            if (StringUtils.isNotEmpty(appId)) {
                vo.setAppid(appId);
            }
        }
        vo.setClient_msg_id(ToolsUtils.bitRandom(4));
        vo.setData(getPublicDeviceAlertMsg());
        if (StringUtils.isNotEmpty(vo.getData())) {
            vo.setData(vo.getData());
        }
        return WeChatUtil.publicTemplateMessageSend(vo);
    }

    @Override
    public String timerWxPublicSendSubscribeInfo() {
        String sql="SELECT open_id,remark FROM wx_user w WHERE EXISTS(SELECT 1 FROM `user` u WHERE trim(u.remark)=trim(w.remark))";
        List<Map<String, Object>> maps = SqlRunner.db().selectList(sql);
        if (maps != null && !maps.isEmpty()) {
           for(Map<String, Object> map:maps){
               TemplateMsgVo vo=new TemplateMsgVo();
               vo.setTouser(map.get("open_id").toString());
               vo.setData(getPublicDeviceAlertMsgByUser(map.get("remark").toString()));
               return wxPublicSendSubscribeInfo(vo);
           }
        }
        //获得要发送用的openid
        return "";
    }

    @Override
    public String syncWxPublicUser() {
        Map<String, String> confMap = getPublicAccessTokenByConfig();
        if (StringUtils.isEmpty(confMap.get("access_token"))) {
            return "没有查到配置token";
        }
        if (StringUtils.isEmpty(confMap.get("app_id"))) {
            return "没有查到配置appId";
        }
        WxCodeVO vo = new WxCodeVO();
        vo.setAuthorizerAccessToken(confMap.get("access_token"));
        //查询
        List<WxUserSyncTaskDTO> list = getWxUserSyncTaskByAppId(confMap.get("app_id"));
        if(CollectionUtil.isNotEmpty(list)) {
            vo.setNextOpenId(list.get(0).getNextOpenid());
        }
        JSONObject jsonObject = WeChatUtil.getPublicOpenidList(vo);
        String msg=handleWxUserSyncInfo(confMap,   jsonObject );
        if(StringUtils.isNotEmpty(msg)){
            return  msg;
        }
        return null;
    }

    /**
     * 保存公众号微信用户信息
     * @param openId
     * @return
     */
    public String instertAndUpdateWxuser(String openId,String token){
        WxUserDTO wxUserDTO = new WxUserDTO();
        wxUserDTO.setType(WxUserTypeEnum.getValue("公从号注册用户"));
        wxUserDTO.setOpenId(openId);
        wxUserDTO.setStatus(StatusEnum.getValue("启用"));
        //是否认证，0，未认证，1已认证
        wxUserDTO.setIsAuth("1");
        //拉取用户的明细信息
        JSONObject jsonObject=WeChatUtil.getPublicUserInfo(token,openId);
        if(null==jsonObject){
            return  "没有获得公众号用户详细信息";
        }
        wxUserDTO.setRemark(jsonObject.getString("remark"));
        //是否关注 ,订阅
        wxUserDTO.setIsFollow(jsonObject.getInteger("subscribe"));
        //订阅时间
        Long timestamp = jsonObject.getLong("subscribe_time");
        Instant instant = Instant.ofEpochMilli(timestamp);
        Date date = Date.from(instant);
        wxUserDTO.setApplyTime(date);
        wxUserDTO.setUnionid(jsonObject.getString("unionid"));
        QueryWrapper<WxUserDTO> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.and(wxUserDTOQueryWrapper -> wxUserDTOQueryWrapper.eq("open_id", openId));
        List<WxUserDTO> list1 = wxUserDao.selectList(queryWrapper1);
        if (CollectionUtil.isNotEmpty(list1)) {
            //拉取用户信息详细信息
            int count = wxUserDao.update(wxUserDTO, queryWrapper1);
            if (count == 0) {
                return "更新微信用户信息失败";
            }
        } else {  //添加用户信息
            int count = wxUserDao.insert(wxUserDTO);
            if (count == 0) {
                return "添加微信用户信息失败";
            }
        }
        return "";
    }


    /**
     * 处理同步数据记录状态信息
     * @param confMap
     * @param jsonObject
     * @return
     */
    private  String handleWxUserSyncInfo(Map<String, String> confMap,  JSONObject jsonObject ){
        if (null == jsonObject.getJSONObject("data")) {
            return "没有公众号用户数据";
        }
        WxUserSyncTaskDTO wxUserSyncTaskDTO = new WxUserSyncTaskDTO();
        wxUserSyncTaskDTO.setWxAppid(confMap.get("app_id"));
        wxUserSyncTaskDTO.setSyncStartTime(DateUtils.date2Str(new Date()));
        wxUserSyncTaskDTO.setSyncTotalNum(ConvertUtils.toInt(jsonObject.getString("total")));
        JSONObject dataJson=jsonObject.getJSONObject("data");
        JSONArray openidsArray = dataJson.getJSONArray("openid");
        if (null == openidsArray) {
            return "没有公众号用户的openid 数据";
        }
        wxUserSyncTaskDTO.setSyncLocalNum(openidsArray.size());
        wxUserSyncTaskDTO.setNextOpenid(jsonObject.getString("next_openid"));
        List<WxUserSyncTaskDTO> list = getWxUserSyncTaskByAppId(confMap.get("app_id"));
        if (CollectionUtil.isNotEmpty(list)) {
            QueryWrapper<WxUserSyncTaskDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(wxUserSyncTaskDTOQueryWrapper -> wxUserSyncTaskDTOQueryWrapper.eq("wx_appid", confMap.get("app_id")));
            int count = wxUserSyncTaskDao.update(wxUserSyncTaskDTO, queryWrapper);
            if (count == 0) {
                return "更新微信同步信息失败";
            }
        } else {
            int count = wxUserSyncTaskDao.insert(wxUserSyncTaskDTO);
            if (count == 0) {
                return "添加微信同步信息失败";
            }
        }
        //更新微信用户数据
        for (int i = 0; i < openidsArray.size(); i++) {
            String openId = openidsArray.getString(i);
            String msg1=instertAndUpdateWxuser(openId,confMap.get("access_token"));
            if(StringUtils.isNotEmpty(msg1)){
                return msg1;
            }

        }

        return "";
    }

    /**
     * 根据APPID 获得微信同步的数据信息
     * @param appid
     * @return
     */
    private List<WxUserSyncTaskDTO> getWxUserSyncTaskByAppId(String appid){
        QueryWrapper<WxUserSyncTaskDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wxUserSyncTaskDTOQueryWrapper -> wxUserSyncTaskDTOQueryWrapper.eq("wx_appid", appid));
        List<WxUserSyncTaskDTO> list = wxUserSyncTaskDao.selectList(queryWrapper);
        return list;
    }

    /**
     * 公众号模板ID
     * puQwC4jJAdEpuaDOYvqlhJny9VH0298kQTYaXkLvl5I
     * 模板编号
     * 43033
     * 标题
     * 设备异常提醒
     * 所属类目
     * 设备管理
     * 操作人
     * 🎋****平安 2023-12-22 添加
     * 场景说明
     * 设备在线时异常出现报警
     * 详细内容
     * <p>
     * 设备ID
     * {{character_string2.DATA}}
     * 设备名称
     * {{thing6.DATA}}
     * 设备地址
     * {{thing9.DATA}}
     * 异常类型
     * {{phrase13.DATA}}
     *
     * @return
     */
    private String getPublicDeviceAlertMsg() {
        JSONObject data = new JSONObject();
        JSONObject character_string2 = new JSONObject();//character_string4必须和模板消息的字段id一致，以下同样，必须要一致，注意时间格式，详情看图二
        character_string2.put("value", "JFSHX12071");
        data.put("character_string2", character_string2);
        //设备名称
        JSONObject thing6 = new JSONObject();
        thing6.put("value", "环测网关1");
        data.put("thing6", thing6);
        //设备地址
        JSONObject thing9 = new JSONObject();
        thing9.put("value", "浙江省杭州市西湖区1！");
        data.put("thing9", thing9);
        JSONObject phrase13 = new JSONObject();
        phrase13.put("value", "设备离线");
        data.put("phrase13", phrase13);
        return JSON.toJSONString(data);
    }

    private String getPublicDeviceAlertMsgByUser(String remark) {
        String sql1 = "select m_id from merchant" + " where m_id="+remark;
        Map<String, Object> map = SqlRunner.db().selectOne(sql1);
        JSONObject data = new JSONObject();
        JSONObject character_string2 = new JSONObject();//character_string4必须和模板消息的字段id一致，以下同样，必须要一致，注意时间格式，详情看图二
        character_string2.put("value", "JFSHX12071");
        data.put("character_string2", character_string2);
        //设备名称
        JSONObject thing6 = new JSONObject();
        thing6.put("value", "环测网关1");
        data.put("thing6", thing6);
        //设备地址
        JSONObject thing9 = new JSONObject();
        thing9.put("value", "浙江省杭州市西湖区1！");
        data.put("thing9", thing9);
        JSONObject phrase13 = new JSONObject();
        phrase13.put("value", "设备离线");
        data.put("phrase13", phrase13);
        return JSON.toJSONString(data);
    }

    @Override
    public Map<String, Object> getWxSmallPrgLoginInfo(String code) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> configMap = sysParamService.getConfigMapByType("wx_small_config");
        String appId = configMap.get("app_id");
        String secret = configMap.get("secret");
        User user = new User();
        List<UserInfoDTO> userInfos = new ArrayList<>();
        Map<String, Object> tokenMap = new HashMap<>();
        WxSmallAuthorizationVO wxSmallAuthorizationVO = WeChatUtil.getJscode2session(appId, secret, code);
        if (StringUtils.isNotEmpty(wxSmallAuthorizationVO.getOpenid())) {
            UserQuery userQuery = new UserQuery();
            userQuery.createCriteria().andOpenIdEqualTo(wxSmallAuthorizationVO.getOpenid());
            List<User> existUsers = userDao.findListByWhere(userQuery);
            if (CollectionUtil.isNotEmpty(existUsers)) {
                user = existUsers.get(0);
                tokenMap = shiroService.createToken(user.getUserId());
                userInfos = userInfoDao.selectByuserId(user.getUserId());
            }
        }
        if (CollectionUtil.isEmpty(userInfos)) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfos.add(userInfoDTO);
        }
        resultMap.put("wxAuthorizationInfo", wxSmallAuthorizationVO);
        resultMap.put("user", user);
        resultMap.put("userInfo", userInfos);
        resultMap.put("token", tokenMap);
        return resultMap;
    }

    /**
     * 手机号快捷登录
     *
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> getWxSmallPhoneLoginInfo(String code, String openId, String username, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        UserVo user = new UserVo();
        Map<String, String> configMap = getWxSmallAccessTokenByConfig();
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(configMap.get("access_token")) && StringUtils.isNotEmpty(configMap.get("app_id"))) {
            Map<String, String> userPhoneMap = WeChatUtil.getuserphonenumber(configMap.get("access_token"), code, openId);
            if (userPhoneMap == null) {
                return resultMap;
            }
            if (StringUtils.isNumeric(userPhoneMap.get("phoneNumber"))) {
                resultMap.put("wxUserPhone", userPhoneMap);
            }
            user.setTelephone(userPhoneMap.get("phoneNumber"));
            user.setOpenId(openId);
            user.setWxAppid(configMap.get("app_id"));
            user.setUserType(UserTypeEnum.getValue("小程序注册用户"));
        }
        if (StringUtils.isNotEmpty(username)) {
            user.setUsername(username);
        }
        List<UserInfoDTO> userInfos = new ArrayList<>();
        List<User> users = userService.findExistUser(user);
        if (CollectionUtil.isNotEmpty(users)) {
            if (StringUtils.isNotEmpty(password) && !password.equals(users.get(0).getPassword())) {
                //密码错误
                return null;
            }
            userInfos = userInfoDao.selectByuserId(users.get(0).getUserId());
//            user.setUserId(users.get(0).getUserId());
//            user.setUsername(users.get(0).getUsername());
//            user.setName(users.get(0).getName());
//            user.setPassword(users.get(0).getPassword());
//            user.setPhone(users.get(0).getTelephone());
            BeanUtils.copyProperties(users.get(0), user);
            resultMap.put("user", user);
            userService.updateByExampleSelective(user);
        } else {
            if (userService.addUser(user) > 0) {
                user.setPhone(user.getTelephone());
                resultMap.put("user", user);
            }
        }
        if (user.getUserId() != 0) {
            Map<String, Object> tokenMap = shiroService.createToken(user.getUserId());
            resultMap.put("token", tokenMap);
        }
        if (CollectionUtil.isEmpty(userInfos)) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfos.add(userInfoDTO);
        }
        resultMap.put("userInfo", userInfos);
        return resultMap;
    }

    @Override
    public Map<String, String> getWxSmallAccessTokenByConfig() {
        Map<String, String> configMap = sysParamService.getConfigMapByType("wx_small_config");
        String appId = configMap.get("app_id");
        String secret = configMap.get("secret");
        String token = configMap.get("access_token");
        String expiresIn = configMap.get("expires_in");
        String tokenRefreshTime = configMap.get("token_refresh_time");
        //不时间刷新token
        if (!DateUtils.isDate(tokenRefreshTime, DateUtils.DATE_FORMAT)) {
            token = updateWxSmallConfig(appId, secret);
        }
        //超过时间刷新
        if (isTokenOutTime(tokenRefreshTime, expiresIn)) {
            token = updateWxSmallConfig(appId, secret);
        }
        //token = updateWxSmallConfig(appId, secret);
        configMap.put("access_token", token);
        return configMap;
    }

    /**
     * 获取公众号的token
     *
     * @return
     */
    @Override
    public Map<String, String> getPublicAccessTokenByConfig() {
        Map<String, String> configMap = sysParamService.getConfigMapByType("wx_public_config");
        String appId = configMap.get("app_id");
        String secret = configMap.get("secret");
        String token = configMap.get("access_token");
        String expiresIn = configMap.get("expires_in");
        String tokenRefreshTime = configMap.get("token_refresh_time");
        //不时间刷新token
        if (!DateUtils.isDate(tokenRefreshTime, DateUtils.DATE_FORMAT)) {
            token = updateWxPublicConfig(appId, secret);
        }
        //超过时间刷新
        if (isTokenOutTime(tokenRefreshTime, expiresIn)) {
            token = updateWxPublicConfig(appId, secret);
        }
        token = updateWxSmallConfig(appId, secret);
        configMap.put("access_token", token);
        return configMap;
    }

    @Override
    public String getWxSmallQRCode(String path, Integer scene, Integer width) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> wxConfigMap = getWxSmallAccessTokenByConfig();
        String token = wxConfigMap.get("access_token");
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String dirName = sf.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + ".jpg";
        String currentDirectoryPath = System.getProperty("user.dir");
        String filePath = currentDirectoryPath + imagePath + "/" + dirName;
        String pathFileName = WeChatUtil.createwxaqrcode(token, path, scene, width, filePath, fileName);
        if (StringUtils.isEmpty(pathFileName)) {
            return "";
        }
        return dirName + "/" + fileName;
    }


    /**
     * 判断微信小程序是否超时
     *
     * @param tokenRefreshTime
     * @return
     */
    private boolean isTokenOutTime(String tokenRefreshTime, String expiresIn) {
        boolean flag = true;
        Date refreshTime = DateUtils.getDateByString(tokenRefreshTime, DateUtils.DATE_FORMAT);
        Long exiress = (DateUtils.getLongDays(new Date(), refreshTime) / 1000) + 10;
        if (exiress > 0 && exiress > ConvertUtils.toLong(expiresIn)) {
            return true;
        }
        return flag;
    }

    /**
     * 刷新token 保存
     *
     * @param appId
     * @param secret
     * @return
     */
    private String updateWxSmallConfig(String appId, String secret) {
        Map<String, String> wxTokenMap = WeChatUtil.getSmallAccessToken(appId, secret);
        int count = 0;
        //保存token
        SysPramVo sysPramVo = new SysPramVo();
        sysPramVo.setParamType("wx_small_config");
        sysPramVo.setParamCode("access_token");
        sysPramVo.setParamVal(wxTokenMap.get("access_token"));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        //保存expiresIn
        sysPramVo.setParamCode("expires_in");
        sysPramVo.setParamVal(wxTokenMap.get("expires_in"));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        //保存tokenRefreshTime
        sysPramVo.setParamCode("token_refresh_time");
        sysPramVo.setParamVal(DateUtils.date2Str(DateUtils.DATE_FORMAT, new Date()));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        if (count == 0) {
            log.info("updateWxSmallConfig 没有保存成功");
            return "";
        }
        return wxTokenMap.get("access_token");
    }


    /**
     * 刷新token 保存
     *
     * @param appId
     * @param secret
     * @return
     */
    private String updateWxPublicConfig(String appId, String secret) {
        Map<String, String> wxTokenMap = WeChatUtil.getSmallAccessToken(appId, secret);
        int count = 0;
        //保存token
        SysPramVo sysPramVo = new SysPramVo();
        sysPramVo.setParamType("wx_public_config");
        sysPramVo.setParamCode("access_token");
        if (StringUtils.isEmpty(wxTokenMap.get("access_token"))) {
            return "";
        }
        sysPramVo.setParamVal(wxTokenMap.get("access_token"));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        //保存expiresIn
        sysPramVo.setParamCode("expires_in");
        sysPramVo.setParamVal(wxTokenMap.get("expires_in"));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        //保存tokenRefreshTime
        sysPramVo.setParamCode("token_refresh_time");
        sysPramVo.setParamVal(DateUtils.date2Str(DateUtils.DATE_FORMAT, new Date()));
        count = sysParamService.updateByExampleSelective(sysPramVo);
        if (count == 0) {
            log.info("updateWxPublicConfig 没有保存成功");
            return "";
        }
        return wxTokenMap.get("access_token");
    }
}
