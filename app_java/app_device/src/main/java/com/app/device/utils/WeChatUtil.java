package com.app.device.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.device.domain.Wechat.TokenInfoVO;
import com.app.device.domain.Wechat.WxCodeVO;
import com.app.device.domain.Wechat.WxSmallAuthorizationVO;
import com.app.device.domain.Wechat.WxUserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 微信请求工具类
 */
public class WeChatUtil {
	private final static Logger log = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 小程序
     * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。更多使用方法详见小程序登录
     * @param appId
     * @param secret
     * @param jsCode 登录时获取的 code，可通过wx.login获取
     * @return
     */
    public static WxSmallAuthorizationVO getJscode2session(String appId, String secret,String jsCode){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + secret +
                "&js_code=" + jsCode +
                "&grant_type=authorization_code";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr=JSONObject.toJSONString(result.toString());
        log.info("微信_获取access_token结果: {}",resultStr );
        if (resultStr.contains("errcode")) {
            log.error("通过code换取网页授权access_token失败");
            return null;
        }
        WxSmallAuthorizationVO vo = JSONObject.toJavaObject(result, WxSmallAuthorizationVO.class);
        return vo;
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
        String resultStr=JSONObject.toJSONString(result.toString());       
        log.info("微信_获取access_token结果: {}",resultStr );
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
        String resultStr=JSONObject.toJSONString(result.toString());       
        log.info("微信_获取access_token结果: {}",resultStr );
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
    public static TokenInfoVO getJsAccessToken(String appId1,String secret1 ) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId1 +
                "&secret=" + secret1 ;               
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr=JSONObject.toJSONString(result.toString());       
        log.info("微信_获取access_token结果: {}",resultStr );
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
    	if(StringUtils.isEmpty(token)){
    		token=ToolsUtils.readConfigFile("token","token.properties");    		
    	}
    	
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token +
                "&type=jsapi" ;
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取access_token结果成功: {}",resultStr );
        if (resultStr.contains("errcode") && !result.get("errcode").toString().equals("0")) {
        log.error("微信_获取access_token失败"+result);
        }
        if(result!=null && !StringUtils.isEmpty(result.get("ticket"))){        	
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
    public static TokenInfoVO refreshToken(String refresh_token,String appId) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refresh_token + "";
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr=JSONObject.toJSONString(result.toString());
 
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
        String resultStr=JSONObject.toJSONString(result.toString()); 
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
    public static JSONObject getWxgzhInfo(String component_appid,String component_access_token,String appid ) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=COMPONENT_ACCESS_TOKEN";
        //三方平台APPID
        //String component_appid="wx12b55fc225726628";
        //三方平台的 component_access_token
        //String component_access_token="48_UiuR5dwnrCvw6GsaGHMbGTmqWWk2lX9L33O8_0fMTpoG6qJIqCZinCiOU-f99ic7Jm2iikSgzXqQgZoOfCRNiC_8Zi3UgptRhze5ghKkw-adwxdiddaG3scUDvoL-YNYSNE9mA168R-cRjcaNQSbAEACFH";
        url=url.replace("COMPONENT_ACCESS_TOKEN", component_access_token.trim());
        //String authorizer_appid="wx5bec63513ecc2443"; //授权公众号的APPID
        String json="{'component_appid':'"+component_appid.trim()+"','authorizer_appid':'"+appid.trim()+"'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr=JSONObject.toJSONString(result.toString());
 
        log.info("微信_获取getWxgzhInfo结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getWxgzhInfo失败",resultStr);
        }          
        //WxUserInfoVO vo = JSON.parseObject(resultStr, WxUserInfoVO.class); 
        return result; 
    }
    
    /**
     
     * 获取令牌（component_access_token）是第三方平台接口的调用凭据
     * @param componentAppid
     * @param componentAppsecret
     * @return
     */
    public static JSONObject getComponentAccessToken(String componentAppid,String componentAppsecret,String componentVerifyTicket ) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";               
        String json="{'component_appid':'"
        +componentAppid.trim()
        +"','component_appsecret':'"
        +componentAppsecret.trim()
        +"','component_verify_ticket':'"
        +componentVerifyTicket.trim()+"'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取getComponentAccessToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getComponentAccessToken失败",resultStr);
        }
        return result; 
    }
    /**
     * 三方平台获取预授权码
     * @param componentAppid
     * @param componenAccessToken
     * @return
     */
    public static JSONObject getPreAuthCode(String componentAppid,String componenAccessToken ) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=COMPONENT_ACCESS_TOKEN";
        url=url.replace("COMPONENT_ACCESS_TOKEN", componenAccessToken.trim());
        String json="{'component_appid':'"+componentAppid.trim()+"'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取getPreAuthCode结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getPreAuthCode失败",resultStr);
        } 
        return result; 
    }
    
    /**
     * 构建授权URL
     * @param componentAppid
     * @param preAuthCode
     * @return
     */
    public static String getAuthUrl(String componentAppid,String preAuthCode,String redirectUri ) {
        String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?"
        		+ "component_appid="+componentAppid
        		+ "&pre_auth_code="+preAuthCode
        		+ "&redirect_uri="+redirectUri+"&auth_type=";  
        return url; 
    }
    
    /**
    
     * 获取授权的刷新TOKEN（AuthorizerRefreshToken）
     @param wxCode
     */
    public static JSONObject getAuthorizerRefreshToken(WxCodeVO wxCode ) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=COMPONENT_ACCESS_TOKEN";               
        url=url.replace("COMPONENT_ACCESS_TOKEN", wxCode.getComponentAccessToken().trim());
        String json="{'component_appid':'"
        +wxCode.getComponentAppid().trim()       
        +"','authorization_code':'"
        +wxCode.getAuthorizationCode().trim()+"'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取getAuthorizerRefreshToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getAuthorizerRefreshToken失败",resultStr);
        }
        return result; 
    }
    
    /**    
     * 获取授权的授权TOKEN（authorizer_access_token）
     @param wxCode
      COMPONENT_ACCESS_TOKEN 
      component_appid 
      authorizer_appid
      authorizer_refresh_token
     */
    public static JSONObject getAuthorizerAccessToken(WxCodeVO wxCode ) {
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=COMPONENT_ACCESS_TOKEN";               
        url=url.replace("COMPONENT_ACCESS_TOKEN", wxCode.getComponentAccessToken().trim());
        String json="{'component_appid':'"
        +wxCode.getComponentAppid().trim() 
        +"','authorizer_appid':'"
        +wxCode.getAuthorizerAppid().trim()
        +"','authorizer_refresh_token':'"
        +wxCode.getAuthorizerRefreshToken().trim()+"'}";
        JSONObject param = JSON.parseObject(json);
        JSONObject result = HttpClientUtils.httpPost(url, param);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取getAuthorizerRefreshToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getAuthorizerRefreshToken失败",resultStr);
        }
        return result; 
    }
    /**    
     * 通过三方授权获取openID
     * 
     */
    public static JSONObject getOpenidSByComponent(WxCodeVO wxCode ) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=";               
        url=url.replace("ACCESS_TOKEN", wxCode.getAuthorizerAccessToken().trim());      
        JSONObject result = HttpClientUtils.httpGet(url);
        String resultStr=JSONObject.toJSONString(result.toString()); 
        log.info("微信_获取getAuthorizerRefreshToken结果: {}", resultStr);
        if (resultStr.contains("errcode")) {
        	log.error("微信_获取getAuthorizerRefreshToken失败",resultStr);
        }
        return result; 
    }
    
    
    
}
