package com.hdpt.device.domain.Device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "灭火器订阅实体")
public class SubscribeVo {
    @ApiModelProperty(value = "ON-开启订阅，OFF-关闭订阅")
    private String subscribe     ;
    @ApiModelProperty(value = "设备信息")
    private List<String> devices     ;
    @ApiModelProperty(value = "订阅上伟网址")
    private String callback_url   ;
}
