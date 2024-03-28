package com.soft.mapp.basecenter.domain.Wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板消息
 */
@Data
public class TemplateMsgVo {
    @ApiModelProperty(value = "接收者（用户）的 openid"  )
    String touser;
    @ApiModelProperty(value = "模板ID",hidden = true)
    String templateId;
    @ApiModelProperty(value = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用access_token或者authorizer_access_token",hidden = true)
    String accessToken;
    @ApiModelProperty(value = "模板跳转链接（海外账号没有跳转能力）",hidden = true)
    String url;
    @ApiModelProperty(value = "跳小程序所需数据，不需跳小程序可不用传该数据",hidden = true)
    String miniprogram;
    @ApiModelProperty(value = "所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）",hidden = true)
    String appid;
    @ApiModelProperty(value = "所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏",hidden = true)
    String pagepath;
    @ApiModelProperty(value = "模板数据，格式形如 { \"key1\": { \"value\": any }, \"key2\": { \"value\": any } }的object")
    String data;
    @ApiModelProperty(value = "防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填")
    String client_msg_id;
}
