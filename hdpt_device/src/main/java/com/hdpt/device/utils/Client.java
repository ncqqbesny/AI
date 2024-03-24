package com.hdpt.device.utils;

import com.alibaba.fastjson.JSON;
import com.hdpt.device.utils.alipay.AlipayApiException;
import com.hdpt.device.utils.alipay.AlipaySignature;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 简易客户端
 *
 * @author yyw
 */
@Data
public class Client {
    /**
     * http请求工具
     */
    private static HttpTool httpTool = new HttpTool();

    /**
     * 请求url
     */
    private String url;

    /**
     * 平台提供的appKey
     */
    private String appId;

    /**
     * 平台提供的私钥
     */
    private String privateKey;

    /**
     * 请求成功后处理
     */
    private Callback callback;

    /**
     * 创建一个客户端
     *
     * @param url        请求url
     * @param appId      平台提供的appKey
     * @param privateKey 平台提供的私钥
     */
    public Client(String url, String appId, String privateKey) {
        this.url = url;
        this.appId = appId;
        this.privateKey = privateKey;
    }

    /**
     * 创建一个客户端
     *
     * @param url        请求url
     * @param appId      平台提供的appKey
     * @param privateKey 平台提供的私钥
     * @param callback   请求成功后处理
     */
    public Client(String url, String appId, String privateKey, Callback callback) {
        this.url = url;
        this.appId = appId;
        this.privateKey = privateKey;
        this.callback = callback;
    }

    /**
     * 发送请求
     *
     * @param requestBuilder 请求信息
     * @return 返回结果
     */
    public String execute(RequestBuilder requestBuilder) {
        RequestInfo requestInfo = requestBuilder.build(appId, privateKey);
        HttpTool.HTTPMethod httpMethod = requestInfo.getHttpMethod();
        boolean postJson = requestInfo.isPostJson();
        Map<String, ?> form = requestInfo.getForm();
        Map<String, String> header = requestInfo.getHeader();
        String requestUrl = requestInfo.getUrl() != null ? requestInfo.getUrl() : url;
        List<HttpTool.UploadFile> uploadFileList = requestBuilder.getUploadFileList();
        String responseData = null;
        // 发送请求
        try {
            // 如果有上传文件
            if (uploadFileList != null && uploadFileList.size() > 0) {
                responseData = httpTool.requestFile(url, form, header, uploadFileList);
            } else {
                if (httpMethod == HttpTool.HTTPMethod.POST && postJson) {
                    //responseData = httpTool.requestJson(requestUrl, JSON.toJSONString(form), header);
                    responseData = httpTool.requestJson(requestUrl, form, JSON.toJSONString(requestInfo.getBizContent()), header);
                } else {
                    responseData = httpTool.request(requestUrl, form, header, httpMethod);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Callback call = requestBuilder.getCallback();
        if (call == null) {
            call = this.callback;
        }
        if (call != null) {
            call.callback(requestInfo, responseData);
        }
        return responseData;
    }

    /**
     * 发送请求
     *
     * @param requestBuilder 请求信息
     * @return 返回结果
     */
    public InputStream download(RequestBuilder requestBuilder) {
        RequestInfo requestInfo = requestBuilder.build(appId, privateKey);
        HttpTool.HTTPMethod httpMethod = requestInfo.getHttpMethod();
        boolean postJson = requestInfo.isPostJson();
        Map<String, ?> form = requestInfo.getForm();
        Map<String, String> header = requestInfo.getHeader();
        String requestUrl = requestInfo.getUrl() != null ? requestInfo.getUrl() : url;
        List<HttpTool.UploadFile> uploadFileList = requestBuilder.getUploadFileList();
        InputStream responseData = null;
        // 发送请求
        try {
            // 如果有上传文件
            if (uploadFileList != null && uploadFileList.size() > 0) {
                responseData = httpTool.downloadByRequestFile(url, form, header, uploadFileList);
            } else {
                if (httpMethod == HttpTool.HTTPMethod.POST && postJson) {
                    responseData = httpTool.downloadJson(requestUrl, JSON.toJSONString(form), header);
                } else {
                    responseData = httpTool.download(requestUrl, form, header, httpMethod);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseData;
    }

    public interface Callback {
        /**
         * 请求成功后回调
         *
         * @param requestInfo  请求信息
         * @param responseData 返回结果
         */
        void callback(RequestInfo requestInfo, String responseData);
    }

    public interface DownloadCallback {
        /**
         * 请求成功后回调
         *
         * @param requestInfo  请求信息
         * @param responseData 返回结果
         */
        void callback(RequestInfo requestInfo, InputStream responseData);
    }

    public static class RequestBuilder {
        private static final String DEFAULT_VERSION = "1.0";

        private String url;
        private String method;
        private String version = DEFAULT_VERSION;
        private Map<String, Object> bizContent;
        private HttpTool.HTTPMethod httpMethod;
        private Map<String, String> header;
        private boolean ignoreSign;
        private boolean postJson;
        private String appAuthToken;
        private List<HttpTool.UploadFile> uploadFileList;
        private Callback callback;
        private Map<String, String> params;

        /**
         * 设置请求url，如果指定了将会优先使用，不指定默认使用Client中的url
         *
         * @param url url
         * @return 返回RequestBuilder
         */
        public RequestBuilder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置方法名
         *
         * @param method 方法名
         * @return 返回RequestBuilder
         */
        public RequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * 设置版本号
         *
         * @param version 版本号
         * @return 返回RequestBuilder
         */
        public RequestBuilder version(String version) {
            this.version = version;
            return this;
        }

        /**
         * 设置业务参数
         *
         * @param bizContent 业务参数
         * @return 返回RequestBuilder
         */
        public RequestBuilder bizContent(Map<String, Object> bizContent) {
            this.bizContent = bizContent;
            return this;
        }

        /**
         * 设置请求方法
         *
         * @param httpMethod 请求方法
         * @return 返回RequestBuilder
         */
        public RequestBuilder httpMethod(HttpTool.HTTPMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        /**
         * 设置请求头
         *
         * @param header 请求头
         * @return 返回RequestBuilder
         */
        public RequestBuilder header(Map<String, String> header) {
            this.header = header;
            return this;
        }

        /**
         * 设置参数map
         *
         * @param params 参数map
         * @return 返回RequestBuilder
         */
        public RequestBuilder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        /**
         * 是否忽略签名验证
         *
         * @param ignoreSign 设置true，不会构建sign字段
         * @return 返回RequestBuilder
         */
        public RequestBuilder ignoreSign(boolean ignoreSign) {
            this.ignoreSign = ignoreSign;
            return this;
        }

        /**
         * 是否是json请求
         *
         * @param postJson 设置true，请求方式变成json（application/json）
         * @return 返回RequestBuilder
         */
        public RequestBuilder postJson(boolean postJson) {
            this.postJson = postJson;
            if (postJson) {
                this.httpMethod(HttpTool.HTTPMethod.POST);
            }
            return this;
        }

        /**
         * 设置token
         *
         * @param appAuthToken 给定的token
         * @return 返回RequestBuilder
         */
        public RequestBuilder appAuthToken(String appAuthToken) {
            this.appAuthToken = appAuthToken;
            return this;
        }

        /**
         * 添加文件
         *
         * @param paramName 表单名称
         * @param file      文件
         * @return 返回RequestBuilder
         */
        public RequestBuilder addFile(String paramName, File file) {
            try {
                HttpTool.UploadFile uploadFile = new HttpTool.UploadFile(paramName, file);
                getUploadFileList().add(uploadFile);
            } catch (IOException e) {
                throw new IllegalArgumentException("上传文件错误", e);
            }
            return this;
        }

        /**
         * 添加文件
         *
         * @param paramName       表单名称
         * @param fileName        文件名称
         * @param fileInputStream 文件流
         * @return 返回RequestBuilder
         */
        public RequestBuilder addFile(String paramName, String fileName, InputStream fileInputStream) {
            try {
                HttpTool.UploadFile uploadFile = new HttpTool.UploadFile(paramName, fileName, fileInputStream);
                getUploadFileList().add(uploadFile);
            } catch (IOException e) {
                throw new IllegalArgumentException("上传文件错误", e);
            }
            return this;
        }

        /**
         * 添加文件
         *
         * @param paramName 表单名称
         * @param fileName  文件名称
         * @param fileData  文件数据
         * @return 返回RequestBuilder
         */
        public RequestBuilder addFile(String paramName, String fileName, byte[] fileData) {
            HttpTool.UploadFile uploadFile = new HttpTool.UploadFile(paramName, fileName, fileData);
            getUploadFileList().add(uploadFile);
            return this;
        }

        private List<HttpTool.UploadFile> getUploadFileList() {
            if (uploadFileList == null) {
                uploadFileList = new ArrayList<>(16);
            }
            return uploadFileList;
        }

        /**
         * 设置请求成功处理
         *
         * @param callback 回调处理
         * @return 返回RequestBuilder
         */
        public RequestBuilder callback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public static void main(String[] args) {
            String url = "http://devops.robustel.cn:3095";
            //String url = "http://localhost:8888";
            String appId = "20200519712301412226170880";
            String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCs8/+KqqfeU14IUN9dfnY6h1+5Mh6ZZBl1uSOazbj+Kz58v28KBCWiIzaFXmiG3DuJaawoZp67OIxjoj4Nzg09qlYIgjxzV6/7qtXmU3pj2h2wZdYJEHHkwMIpE86qIRX4qfUGGLCXjaJrGb1AeDs5WTaAAMkR5UAfhUINzGJ4VkGTEVBuCQh3BNBbPeWUKTg7ZtPRxdpgd9Kk9fAkPGMpAcfeDrKkaXihKgtZrcteW2ivXCKQFPSNSOmZkxlHUScANBdczR8qzWn3HtOtFoXcq/t5kWEsCsjcmcOtA80Vro7gQAiDt6kEBO38RNQ/D1zKeu29uGKNBDk/AohlVoKNAgMBAAECggEBAKpkAKBmnNemHn/m3QhJLMVg1Weo/whz27llWrSKdPTDOd7A4u5lQ4ZHVtFyP1yN4IHyf8+VulAM2BLYpnYHSHg2NmhPlqqz+wbgObefCncRTiqZSZLIhsytMxwxDWwDCJhhKKqG7IOC4D7PrumzCfXn9I+LNozQ5KmpKXhaobCW9HgOgEJ/FgC67XB6BjomGJXhHzta5+aNDg0HBlg+yMD6y+cuJgTNU4FzCpcXFfYKp6BCSzRM0IxbD+wYa5pl3gr0kBuJS/5+aoFTRjVVDFTcbuN6PFB/w0Igcex+787pxz1LhTfjc7BJbtFyLb6d01Vu7+HxZoLT5jbbW9HusekCgYEA/Q4yRgAUhyr35I8xeQBBdnA6l8zIUJPbv8XiBdcgvEd8zxaLV3JWu8VWkkax0CqKpBNpLhTkkBcIddbF2G1oJfT/YzvTJr+WcrUlvC+od81K8UGpRxIHByaKbl+oLrl+9nn5GyLOSOFgE6+YHFvpBXgwhC/v2GBVTn/9Y3vBprMCgYEArvcxPZK+jmHQdISwk9yUzsJS/8pNBbar/pgqJSjbKYbHml3MwBdsalhm89gI8UMFxcd37uFfjlGuRZi5ejh30b0kTvF6mEEMynR4p0u2apQG4t7nvvXw/t3kjy2228UFO7wXUnybpgvNLf7tIL+uYNMJ+haNe/ST62jGyfex0b8CgYByZceOY5zkGiWkmqM320i8vTGPzFDGNIGEKXXrQ/yVgQ/IIp3tc1AIZMalK/ZYb9LrJ6dF4MYO6Yq2ktgv5g3OEvqdJr9m5feidYfQkg7r0c/OTuuyqFCrB95O1UmXjd36mNtnTA3LXq9rVtdCAr7N7S6po24e+DW4Xh3zqk+6BwKBgQCTQa0Ju5Xx09VUG9FZwQ7lLZwZeLR2mJYNgxgFimtaeftIqqWHDq/KmXicxjhQhi43lXgVb02zRH++R5njid8egWgroQWjWyxewMRDWW/AJ7HcA+tcRzZ761lQQBNkyF9tNRMT85g0mj4n9iSxqWIkT7pX599QcjqEqPyWQ4g/lQKBgBa5aPzuTgdIpOWE2u6R63HJBbAWjlip9QNsF/5RUrfqe776aVAr5e+1SfXJaysbL1jjStlfrGYcT37U6rg189Pfwu4WNe5tIKyq+fakrsMsCy5TVozpn2wpJNLWfbSHI9mxK+1owFQoDiMk3AonlALN29WEqg/7T+R5CGuZ+V2+";
            Client client = new Client(url, appId, privateKey);

            RequestInfo requestInfo = new RequestBuilder().build(appId,privateKey);

            System.out.println(requestInfo);

            System.out.println(UUID.randomUUID().toString());

        }
        public Callback getCallback() {
            return callback;
        }

        public RequestInfo build(String appId, String privateKey) {
            // 公共请求参数
            if(params == null){
                params = new HashMap<String, String>();
            }
            params.put("clientId", appId);
            if (method != null) {
                //params.put("method", method);
            }
            if (version != null) {
                params.put("apiVersion", version);
            }else{
                params.put("apiVersion", "1.0");
            }
            if (appAuthToken != null) {
                params.put("app_auth_token", appAuthToken);
            }
            params.put("format", "json");
            params.put("charset", "utf-8");
            params.put("sign_type", "RSA2");
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));

            // 业务参数
            //params.put("uniqueCode", UUID.randomUUID().toString().replace("-", ""));
            //params.put("signatureVersion","1.0");

            if (!ignoreSign) {
                String content = AlipaySignature.getSignContent(params);
                String sign = null;
                try {
                    sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
                } catch (AlipayApiException e) {
                    throw new RuntimeException(e);
                }
                params.put("signature", sign);
            }

            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setUrl(url);
            requestInfo.setMethod(method);
            requestInfo.setVersion(version);
            requestInfo.setForm(params);
            requestInfo.setHeader(header);
            requestInfo.setBizContent(bizContent);
            requestInfo.setPostJson(postJson);
            requestInfo.setHttpMethod(httpMethod);
            return requestInfo;
        }
        /*public RequestInfo build(String appId, String privateKey) {
            // 公共请求参数
            if(params == null){
                params = new HashMap<String, String>();
            }
            params.put("app_id", appId);
            if (method != null) {
                params.put("method", method);
            }
            if (version != null) {
                params.put("version", version);
            }
            if (appAuthToken != null) {
                params.put("app_auth_token", appAuthToken);
            }
            params.put("format", "json");
            params.put("charset", "utf-8");
            params.put("sign_type", "RSA2");
            params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            // 业务参数
            params.put("biz_content", JSON.toJSONString(bizContent == null ? Collections.emptyMap() : bizContent));

            if (!ignoreSign) {
                String content = AlipaySignature.getSignContent(params);
                String sign = null;
                try {
                    sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
                } catch (AlipayApiException e) {
                    throw new RuntimeException(e);
                }
                params.put("sign", sign);
            }

            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setUrl(url);
            requestInfo.setMethod(method);
            requestInfo.setVersion(version);
            requestInfo.setForm(params);
            requestInfo.setHeader(header);
            requestInfo.setPostJson(postJson);
            requestInfo.setHttpMethod(httpMethod);
            return requestInfo;
        }*/
    }

    @Data
    public static class RequestInfo {
        private String url;
        private String method;
        private String version;
        private boolean postJson;
        private Map<String, ?> form;
        private Map<String, ?> bizContent;
        private Map<String, String> header;
        private HttpTool.HTTPMethod httpMethod;

        /**
         * 返回json跟节点名称
         *
         * @return 返回json跟节点名称
         */
        public String getDataNode() {
            return method == null ? null : method.replace('.', '_') + "_response";
        }
    }

}
