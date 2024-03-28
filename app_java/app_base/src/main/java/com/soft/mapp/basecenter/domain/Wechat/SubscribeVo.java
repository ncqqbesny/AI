package com.soft.mapp.basecenter.domain.Wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订阅发送信息VO
 */
@Data
public class SubscribeVo {
    @ApiModelProperty(value = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用access_token或者authorizer_access_token")
    String accessToken;
    @ApiModelProperty(value = "所需下发的订阅模板id")
    String templateId;
    @ApiModelProperty(value = "点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转")
    String page;
    @ApiModelProperty(value = "接收者（用户）的 openid")
    String touser;
    @ApiModelProperty(value = "模板内容，格式形如 { \"key1\": { \"value\": any }, \"key2\": { \"value\": any } }的object")
    String data;
    @ApiModelProperty(value = "跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版")
    String miniprogramState;
    @ApiModelProperty(value = "进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN")
    String lang;

}
