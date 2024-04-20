package com.app.device.domain.Device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "灭火器控制命令实体")
public class RemoteCtrlVo {
    @ApiModelProperty(value = "设备序列号")
    private String serialNo     ;
    @ApiModelProperty(value = "上送数据时间间隔")
    private String interval     ;
    @ApiModelProperty(value = "上送时间")
    private String reportTime   ;
    @ApiModelProperty(value = "数据发送任务标志")
    private String reportFlag   ;
    @ApiModelProperty(value = "蜂鸣器控制")
    private String buzzer       ;
    @ApiModelProperty(value = "控制指令")
    private String cmd          ;
    @ApiModelProperty(value = "通讯心跳")
    private String keepalive    ;
}
