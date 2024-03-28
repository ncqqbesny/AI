package com.soft.mapp.basecenter.utils;

import cn.hutool.core.util.ObjectUtil;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

public class HttpTools {
    //get请求
    public static String doGet(String url, String authValue) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-type", "application/json");
            if (!ObjectUtil.isNull(authValue)) {
                httpGet.setHeader("Authorization", "Bearer " + authValue);
            }
            result = httpClient.execute(httpGet, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    // post请求参数为json格式
    public static String doJsonPost(String url, String json, String authValue) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            if (!ObjectUtil.isNull(authValue)) {
                httpPost.setHeader("Authorization", "Bearer " + authValue);
            }
            httpPost.setEntity(requestEntity);
            result = httpClient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
    /**
     * 获取请求的ip
     */
    public static String getRequestIp() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
