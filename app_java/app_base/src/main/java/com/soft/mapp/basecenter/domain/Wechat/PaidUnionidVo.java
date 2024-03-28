package com.soft.mapp.basecenter.domain.Wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取小程序Unionid
 */
@Data
public class PaidUnionidVo {
    @ApiModelProperty(value = "接口调用凭证，该参数为 URL 参数，非 Body 参数。使用access_token或者authorizer_access_token",hidden = true)
    String accessToken;
    @ApiModelProperty(value = "支付用户唯一标识"  )
    String openid;
    @ApiModelProperty(value = "微信支付订单号")
    String transactionId;
    @ApiModelProperty(value = "接收者（用户）的 openid")
    String touser;
    @ApiModelProperty(value = "微信支付分配的商户号，和商户订单号配合使用")
    String mchId;
    @ApiModelProperty(value = "跳微信支付商户订单号，和商户号配合使用")
    String outTradeNo;


}
