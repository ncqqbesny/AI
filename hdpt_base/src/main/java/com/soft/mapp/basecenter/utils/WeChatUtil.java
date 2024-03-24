package com.soft.mapp.basecenter.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.soft.mapp.basecenter.domain.Wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信请求工具类
 */
public class WeChatUtil {
    private final static Logger log = LoggerFactory.getLogger(WeChatUtil.class);



    /**
     * 调用微信开放接口subscribeMessage.send发送订阅消息(固定模板的订阅消息) 小程序发送
     * POST https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN
     */
    public static String publicTemplateMessageSend(TemplateMsgVo templateMsgVo) {
        HttpURLConnection httpConn = null;
        InputStream is = null;
        BufferedReader rd = null;
        String accessToken = null;
        String str = null;
        try {
            //获取token  小程序全局唯一后台接口调用凭据
            accessToken = templateMsgVo.getAccessToken();
            JSONObject xmlData = new JSONObject();
            xmlData.put("touser", templateMsgVo.getTouser());//接收者（用户）的 openid 必填
            xmlData.put("template_id", templateMsgVo.getTemplateId());//所需下发的订阅模板id 必填
            //模板跳转链接（海外账号没有跳转能力）
            xmlData.put("url", templateMsgVo.getUrl());
            //防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
            xmlData.put("client_msg_id", templateMsgVo.getClient_msg_id());
            //跳小程序所需数据，不需跳小程序可不用传该数据
            JSONObject miniprogram = new JSONObject();
            miniprogram.put("appid", templateMsgVo.getAppid());
            miniprogram.put("pagepath", templateMsgVo.getPagepath());
            xmlData.put("miniprogram", miniprogram);
            /**
             * 类目模板消息参数值内容限制说明
             thing.DATA:20个以内字符;可汉字、数字、字母或符号组合
             time.DATA:24小时制时间格式（支持+年月日），支持填时间段，两个时间点之间用“~”符号连接
             */

            JSONObject data = JSONObject.parseObject(templateMsgVo.getData());
            xmlData.put("data", data);//公从号模板数据
            System.out.println("发送模板消息xmlData:" + xmlData);
            URL url = new URL(
                    "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                            + accessToken);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Host", "https://api.weixin.qq.com");
            // httpConn.setRequestProperty("Content-Length", String.valueOf(xmlData.));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=\"UTF-8\"");
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream out = httpConn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(xmlData.toString());
            osw.flush();
            osw.close();
            out.close();
            is = httpConn.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while ((str = rd.readLine()) != null) {
                System.out.println("返回数据：" + str);
            }
        } catch (Exception e) {
            return "发送模板消息失败.." + e.getMessage();
        }
        return "";
    }


    /**
     * 调用微信开放接口subscribeMessage.send发送订阅消息(固定模板的订阅消息) 小程序发送
     * POST https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN
     */
    public static void subscribeSend(SubscribeVo subscribeVo) {
        HttpURLConnection httpConn = null;
        InputStream is = null;
        BufferedReader rd = null;
        String accessToken = null;
        String str = null;
        try {
            //获取token  小程序全局唯一后台接口调用凭据
            accessToken = subscribeVo.getAccessToken();

            JSONObject xmlData = new JSONObject();
            xmlData.put("touser", subscribeVo.getTouser());//接收者（用户）的 openid
            xmlData.put("template_id", subscribeVo.getTemplateId());//所需下发的订阅模板id
            xmlData.put("page", subscribeVo.getPage());//点击模板卡片后的跳转页面，仅限本小程序内的页面
            xmlData.put("miniprogram_state", subscribeVo.getMiniprogramState());//跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
            xmlData.put("lang", subscribeVo.getLang());//进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN返回值

            /**
             * 订阅消息参数值内容限制说明
             thing.DATA:20个以内字符;可汉字、数字、字母或符号组合
             time.DATA:24小时制时间格式（支持+年月日），支持填时间段，两个时间点之间用“~”符号连接
             */

            JSONObject data = new JSONObject();
            //取餐号
            JSONObject character_string1 = new JSONObject();//character_string4必须和模板消息的字段id一致，以下同样，必须要一致，注意时间格式，详情看图二
            character_string1.put("value", "JFSHX12071");
            data.put("character_string1", character_string1);
            //购买商品
            JSONObject thing2 = new JSONObject();
            thing2.put("value", "环测网关1");
            data.put("thing2", thing2);
            //温馨提示
            JSONObject thing3 = new JSONObject();
            thing3.put("value", "浙江省杭州市西湖区1！");
            data.put("thing3", thing3);
            JSONObject thing4 = new JSONObject();
            thing4.put("value", "设备在线1！");
            data.put("thing4", thing4);

            xmlData.put("data", data);//小程序模板数据

            System.out.println("发送模板消息xmlData:" + xmlData);
            URL url = new URL(
                    "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="
                            + accessToken);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Host", "https://api.weixin.qq.com");
            // httpConn.setRequestProperty("Content-Length", String.valueOf(xmlData.));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=\"UTF-8\"");
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream out = httpConn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(xmlData.toString());
            osw.flush();
            osw.close();
            out.close();
            is = httpConn.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while ((str = rd.readLine()) != null) {
                System.out.println("返回数据：" + str);
            }
        } catch (Exception e) {
            System.out.println("发送模板消息失败.." + e.getMessage());
        }
    }


    /**
     * 获取小程序二维码
     *
     * @param accessToken 接口调用凭证
     * @return
     */
    public static String createwxaqrcode(String accessToken, String path, Integer scene, Integer width, String imagePath, String imageFileName) {
        try {
            //获取小程序码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制

            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacode?access_token=" + accessToken);
            //获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。
            if (null != scene) {
                url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("path", path);
            if (StringUtil.isEmpty(width)) {
                paramJson.put("width", 430);
            } else {
                paramJson.put("width", width);
            }
            if (null != scene) {
                paramJson.put("auto_color", true);
                //默认是false，是否需要透明底色，为 true 时，生成透明底色的小程序
                paramJson.put("is_hyaline", true);
                paramJson.put("check_path", true);
                paramJson.put("scene", scene);
            }

            //设置小程序码版本
            //paramJson.put("env_version","release"); 默认正式
            //paramJson.put("env_version","trial"); 体验版
            //paramJson.put("env_version","develop"); 开发版
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            String contentType = httpURLConnection.getContentType();
            if (contentType.contains("json")) {
                log.info("调用微信小程序生成接口出错,token失效");
                throw new IllegalArgumentException("调用微信小程序生成接口出错,token失效");
            } else {
                //开始获取数据
                //此处根据具体需要返回的值 return对应给前端
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    /*BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String letter;
                    StringBuilder str = new StringBuilder();
                    while ((letter = br.readLine()) != null){
                        str.append(letter);
                    }
                    br.close();
                    httpURLConnection.disconnect();
                    String sr = str.toString();
                    JSONObject jsonObject = (JSONObject) JSON.parse(sr);
                    return (String) jsonObject.get("openlink");*/
                    InputStream in = httpURLConnection.getInputStream();
                    return PicUtils.inputStreamToJpg(imagePath, imageFileName, in);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 该接口用于将code换取用户手机号。 说明，每个code只能使用一次，code的有效期为5min。
     *
     * @param accessToken 接口调用凭证
     * @param code        手机号获取凭证
     * @return
     */
    public static Map<String, String> getuserphonenumber(String accessToken, String code, String openid) {
        Map<String, String> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        String json = "{'code':'" + code + "','openid':'" + openid + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        log.info("微信_获取getuserphonenumber结果: {}", result);
        if (result != null && result.getString("errcode") != null && !result.getString("errcode").equals("0")) {
            log.error("微信_获取getuserphonenumber失败", result);
            return null;
        }
        JSONObject phoneInfoJson = result.getJSONObject("phone_info");
        if (phoneInfoJson == null) {
            log.error("微信_获取getuserphonenumber--phone_info失败", result);
            return null;
        }
        map.put("phoneNumber", phoneInfoJson.getString("phoneNumber"));
        map.put("purePhoneNumber", phoneInfoJson.getString("purePhoneNumber"));
        map.put("countryCode", phoneInfoJson.getString("countryCode"));
        JSONObject watermarkJson = phoneInfoJson.getJSONObject("watermark");
        if (watermarkJson == null) {
            log.error("微信_获取getuserphonenumber--watermark失败", result);
            return null;
        }
        map.put("timestamp", watermarkJson.getString("timestamp"));
        map.put("appid", watermarkJson.getString("appid"));

        return map;
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据，token有效期为7200s.
     *
     * @param appId
     * @param secret
     * @return
     */
    public static Map<String, String> getSmallAccessToken(String appId, String secret) {
        Map<String, String> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId +
                "&secret=" + secret +
                "&grant_type=client_credential";
        JSONObject result = HttpClientUtils.httpGet(url);
        log.info("微信获取小程序access_token结果: {}", result);
        if (result == null || StringUtil.isNotEmptyString(result.getString("errcode"))) {
            log.error("微信获取小程序access_token结果失败");
            return null;
        }
        map.put("access_token", result.getString("access_token"));
        map.put("expires_in", result.getString("expires_in"));
        return map;
    }

    /**
     * 获取公众号全局唯一后台接口调用凭据，token有效期为7200s.
     *
     * @param appId
     * @param secret
     * @return
     */
    public static Map<String, String> getPublicAccessToken(String appId, String secret) {
        Map<String, String> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/cgi-bin/token?appid=" + appId +
                "&secret=" + secret +
                "&grant_type=client_credential";
        JSONObject result = HttpClientUtils.httpGet(url);
        log.info("微信获取公众号access_token结果: {}", result);
        if (result == null || StringUtil.isNotEmptyString(result.getString("errcode"))) {
            log.error("微信获取公众号access_token结果失败");
            return null;
        }
        map.put("access_token", result.getString("access_token"));
        map.put("expires_in", result.getString("expires_in"));
        return map;
    }

    /**
     * 小程序
     * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。更多使用方法详见小程序登录
     *
     * @param appId
     * @param secret
     * @param jsCode 登录时获取的 code，可通过wx.login获取
     * @return
     */
    public static WxSmallAuthorizationVO getJscode2session(String appId, String secret, String jsCode) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + secret +
                "&js_code=" + jsCode +
                "&grant_type=authorization_code";
        JSONObject result = HttpClientUtils.httpGet(url);
        log.info("微信_获取登录凭证校验参数: appid==:" + appId + "--secret==:" + secret + "--jscode==:" + jsCode + "--结果==：" + result);
        WxSmallAuthorizationVO vo = JSONObject.toJavaObject(result, WxSmallAuthorizationVO.class);
        return vo;
    }

    /**
     * 小程序支付后获取 Unionid
     * @param paidUnionidVo
     * @return
     */
    public static String getPaidUnionidBySmall(PaidUnionidVo paidUnionidVo) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.weixin.qq.com/wxa/getpaidunionid?access_token=" + paidUnionidVo.getAccessToken()
                + "&openid=" + paidUnionidVo.getOpenid());
        if (StringUtil.isNotEmpty(paidUnionidVo.getTransactionId())) {
            //微信支付订单号
            sb.append("&transaction_id=" + paidUnionidVo.getTransactionId());
        }
        //微信支付分配的商户号，和商户订单号配合使用
        if (StringUtil.isNotEmpty(paidUnionidVo.getMchId())) {
            sb.append("&mch_id=" + paidUnionidVo.getMchId());
        }
        //微信支付商户订单号，和商户号配合使用
        if (StringUtil.isNotEmpty(paidUnionidVo.getOutTradeNo())) {
            sb.append("&out_trade_no=" + paidUnionidVo.getOutTradeNo());
        }
        JSONObject result = HttpClientUtils.httpGet(sb.toString());
        log.info("微信_支付后获取 Unionid getSmallUnionid:" + JSON.toJSONString(paidUnionidVo) + "----结果：" + result);
        if(!"0".equals(result.getString("errcode"))){
            return "";
        }
        return result.getString("unionid");

    }

    /**
     * 通过code换取网页授权access_token
     *
     * @param code
     * @return
     */
    public static TokenInfoVO getAccessToken(String code, String appId, String secret) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId +
                "&secret=" + secret +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取access_token结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("通过code换取网页授权access_token失败");
            return null;
        }
        TokenInfoVO vo = JSONObject.toJavaObject(result, TokenInfoVO.class);
        //getticket(vo.getAccess_token());
        //writeConfigFile(vo.getAccess_token());
        return vo;
    }

    /**
     * 通过三方授权code获得token
     *
     * @param wxCodeVo
     * @return
     */
    public static TokenInfoVO getAccessTokenByComponent(WxCodeVO wxCodeVo) {
        String url = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=" + wxCodeVo.getAuthorizerAppid() +
                "&code=" + wxCodeVo.getCode() +
                "&component_appid=" + wxCodeVo.getComponentAppid() +
                "&component_access_token=" + wxCodeVo.getComponentAccessToken() +
                "&grant_type=authorization_code";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取access_token结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("通过code换取网页授权access_token失败");
            return null;
        }
        TokenInfoVO vo = JSONObject.toJavaObject(result, TokenInfoVO.class);
        //getticket(vo.getAccess_token());
        //writeConfigFile(vo.getAccess_token());
        return vo;
    }


    /**
     * 通过code换取网页授权access_token
     *
     * @param appId1 secret1
     * @return
     */
    public static TokenInfoVO getJsAccessToken(String appId1, String secret1) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId1 +
                "&secret=" + secret1;
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取access_token结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("通过code换取网页授权access_token失败");
            return null;
        }
        TokenInfoVO vo = JSONObject.toJavaObject(result, TokenInfoVO.class);
        //getticket(vo.getAccess_token());
        //writeConfigFile(vo.getAccess_token());
        return vo;
    }


    public static String getticket(String token) {
        if (StringUtils.isEmpty(token)) {
            token = ToolsUtils.readConfigFile("token", "token.properties");
        }

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token +
                "&type=jsapi";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取access_token结果成功: {}", resultStr);
        if (resultStr.contains("errcode") && !result.get("errcode").toString().equals("0")) {
            log.error("微信_获取access_token失败" + result);
        }
        if (result != null && !StringUtils.isEmpty(result.get("ticket"))) {
            return result.get("ticket").toString();
        }
        return "";

    }

    /**
     * 刷新access_token（如果需要）
     *
     * @param appId
     * @param refresh_token
     * @return
     */
    public static TokenInfoVO refreshToken(String refresh_token, String appId) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refresh_token + "";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());

        log.info("微信_刷新access_token结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("刷新access_token失败");
        }
        TokenInfoVO vo = JSON.parseObject(resultStr, TokenInfoVO.class);
        return vo;

    }


    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * 获取用户信息UNIONID
     *
     * @param access_token
     * @param openid
     * @return
     */
    public static WxUserInfoVO getUserInfo(String access_token, String openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取snsapi_userinfo结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("拉取微信用户信息失败");
        }
        WxUserInfoVO vo = JSONObject.toJavaObject(result, WxUserInfoVO.class);
        //WxUserInfoVO vo = JSON.parseObject(resultStr, WxUserInfoVO.class); 
        return vo;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * 获取用户信息UNIONID
     *
     * @param component_appid
     * @param component_access_token
     * @param appid
     * @return
     */
    public static JSONObject getWxgzhInfo(String component_appid, String component_access_token, String appid) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=COMPONENT_ACCESS_TOKEN";
        //三方平台APPID
        //String component_appid="wx12b55fc225726628";
        //三方平台的 component_access_token
        //String component_access_token="48_UiuR5dwnrCvw6GsaGHMbGTmqWWk2lX9L33O8_0fMTpoG6qJIqCZinCiOU-f99ic7Jm2iikSgzXqQgZoOfCRNiC_8Zi3UgptRhze5ghKkw-adwxdiddaG3scUDvoL-YNYSNE9mA168R-cRjcaNQSbAEACFH";
        url = url.replace("COMPONENT_ACCESS_TOKEN", component_access_token.trim());
        //String authorizer_appid="wx5bec63513ecc2443"; //授权公众号的APPID
        String json = "{'component_appid':'" + component_appid.trim() + "','authorizer_appid':'" + appid.trim() + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr = JSONObject.toJSONString(result.toString());

        log.info("微信_获取getWxgzhInfo结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getWxgzhInfo失败", resultStr);
        }
        //WxUserInfoVO vo = JSON.parseObject(resultStr, WxUserInfoVO.class); 
        return result;
    }

    /**
     * 获取令牌（component_access_token）是第三方平台接口的调用凭据
     *
     * @param componentAppid
     * @param componentAppsecret
     * @return
     */
    public static JSONObject getComponentAccessToken(String componentAppid, String componentAppsecret, String componentVerifyTicket) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        String json = "{'component_appid':'"
                + componentAppid.trim()
                + "','component_appsecret':'"
                + componentAppsecret.trim()
                + "','component_verify_ticket':'"
                + componentVerifyTicket.trim() + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取getComponentAccessToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getComponentAccessToken失败", resultStr);
        }
        return result;
    }

    /**
     * 三方平台获取预授权码
     *
     * @param componentAppid
     * @param componenAccessToken
     * @return
     */
    public static JSONObject getPreAuthCode(String componentAppid, String componenAccessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=COMPONENT_ACCESS_TOKEN";
        url = url.replace("COMPONENT_ACCESS_TOKEN", componenAccessToken.trim());
        String json = "{'component_appid':'" + componentAppid.trim() + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取getPreAuthCode结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getPreAuthCode失败", resultStr);
        }
        return result;
    }

    /**
     * 构建授权URL
     *
     * @param componentAppid
     * @param preAuthCode
     * @return
     */
    public static String getAuthUrl(String componentAppid, String preAuthCode, String redirectUri) {
        String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?"
                + "component_appid=" + componentAppid
                + "&pre_auth_code=" + preAuthCode
                + "&redirect_uri=" + redirectUri + "&auth_type=";
        return url;
    }

    /**
     * 获取授权的刷新TOKEN（AuthorizerRefreshToken）
     *
     * @param wxCode
     */
    public static JSONObject getAuthorizerRefreshToken(WxCodeVO wxCode) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=COMPONENT_ACCESS_TOKEN";
        url = url.replace("COMPONENT_ACCESS_TOKEN", wxCode.getComponentAccessToken().trim());
        String json = "{'component_appid':'"
                + wxCode.getComponentAppid().trim()
                + "','authorization_code':'"
                + wxCode.getAuthorizationCode().trim() + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取getAuthorizerRefreshToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getAuthorizerRefreshToken失败", resultStr);
        }
        return result;
    }

    /**
     * 获取授权的授权TOKEN（authorizer_access_token）
     *
     * @param wxCode COMPONENT_ACCESS_TOKEN
     *               component_appid
     *               authorizer_appid
     *               authorizer_refresh_token
     */
    public static JSONObject getAuthorizerAccessToken(WxCodeVO wxCode) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=COMPONENT_ACCESS_TOKEN";
        url = url.replace("COMPONENT_ACCESS_TOKEN", wxCode.getComponentAccessToken().trim());
        String json = "{'component_appid':'"
                + wxCode.getComponentAppid().trim()
                + "','authorizer_appid':'"
                + wxCode.getAuthorizerAppid().trim()
                + "','authorizer_refresh_token':'"
                + wxCode.getAuthorizerRefreshToken().trim() + "'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取getAuthorizerRefreshToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getAuthorizerRefreshToken失败", resultStr);
        }
        return result;
    }

    /**
     * 通过三方授权获取openID
     * 公众号：用户管理 /获取用户列表 OPEN ID
     */
    public static JSONObject getPublicOpenidList(WxCodeVO wxCode) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        url = url.replace("ACCESS_TOKEN", wxCode.getAuthorizerAccessToken().trim());
        if(StringUtil.isNotEmpty(wxCode.getNextOpenId())) {
            url = url.replace("NEXT_OPENID", wxCode.getNextOpenId());
        }else{
            url = url.replace("NEXT_OPENID", "");
        }
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取获取用户列表 getPublicOpenidList结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取getAuthorizerRefreshToken失败", resultStr);
        }
        return result;
    }

    /**
     * 公众号：获取用户基本信息(UnionID机制)
     */
    public static JSONObject getPublicUserInfo(String token,String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url = url.replace("ACCESS_TOKEN", token);
        url=url.replace("OPENID",openId);
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr = JSONObject.toJSONString(result.toString());
        log.info("微信_获取获取用户基本信息getPublicUserInfo结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
            log.error("微信_获取获取用户基本信息getPublicUserInfo失败", resultStr);
        }
        return result;
    }

}
