package com.app.device.handler;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class TokenUtil {
    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    public static IMessage errorCode;
    /**
     * 计算token，将token放置在session里,防止表单重复提交
     * @param
     * @param request
     */
    public static String getSubmitToken(HttpServletRequest request,String sessionKey) {

        // 计算token，将token放置在session里和返回页面，防止表单重复提交
        //String submitToken = UUID.randomUUID().toString();
        String submitToken = request.getHeader("token");
        if(null==submitToken){
            submitToken=request.getParameter("token");
        }
        request.getSession().setAttribute(sessionKey, submitToken);
        logger.debug("获取新的token，URI:{}，QueryString:{},RequestURl:{}",request.getRequestURI(),request.getQueryString(),request.getRequestURL());
        logger.debug("获取的新token:{}",submitToken);
        logger.debug("获取的新sessionid:{}",request.getSession().getId());
        return submitToken;
    }
    /*
     * 提交订单验证
     */
    public static void submitTokenCheck(String submitToken, HttpServletRequest request,String sessionKey) {
        logger.debug("校验的新sessionid:{}",request.getSession().getId());
        String tokenValue = (String) request.getSession().getAttribute(sessionKey);
        logger.debug("校验token,参数：[submitToken:"+submitToken+",tokenValue:"+tokenValue+"]");
        if (StringUtils.isBlank(submitToken)) {
            throw new BusinessException(errorCode, "请刷新页面重试！");
        }

        // 参数、session中都没用token值提示错误
        if (StringUtils.isBlank(submitToken) && StringUtils.isBlank(tokenValue)) {
            throw new BusinessException(errorCode, "请刷新页面重试！");
        } else if ((!StringUtils.isBlank(submitToken)) && StringUtils.isBlank(tokenValue)) {
            throw new BusinessException(errorCode, "请勿重复提交！");
        }

        synchronized (tokenValue) {
            tokenValue = (String) request.getSession().getAttribute(sessionKey);
            if (tokenValue.equalsIgnoreCase(submitToken)) {
                request.getSession().removeAttribute(sessionKey);
                return;
            } else {
                throw new BusinessException(errorCode, "请勿重复提交！");
            }
        }
    }
}

