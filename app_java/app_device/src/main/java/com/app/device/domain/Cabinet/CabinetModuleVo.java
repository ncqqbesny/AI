package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜模块信息集合显示")
public class CabinetModuleVo {
    @ApiModelProperty(value = "货柜编号", required = true)
    private String cabinetNo;
    @ApiModelProperty(value = "站号", required = true)
    private String stationNo;
    @ApiModelProperty(value = "端口")
    private String port;
    @ApiModelProperty(value = "模组类型，0、PC主板,1、温湿度模组,2、冷凝除湿模组(除湿),3、通风模组(风机),4、加热模组, 14、锁,15、认证（门禁）,16、指纹头, 17、人脸识别相机, 19、大彩屏幕 ,21、RFID扫描设备（SRL5800), 31、RTU2112型通用远程扩展模块")
    private String moduleType;
    @ApiModelProperty(value = "模块地址(例如1-01)", required = true)
    private String  address;
    @ApiModelProperty(value = "模组标识")
    private String modelSign;
    @ApiModelProperty(value = "值为0或1")
    private String DO1;
    @ApiModelProperty(value = "值为0或1")
    private String DO2;
    @ApiModelProperty(value = "值为0或1")
    private String DO3;
    @ApiModelProperty(value = "值为0或1")
    private String DO4;
    @ApiModelProperty(value = "值为0或1")
    private String DO5;
    @ApiModelProperty(value = "值为0或1")
    private String DO6;
    @ApiModelProperty(value = "值为0或1")
    private String DO7;
    @ApiModelProperty(value = "值为0或1")
    private String DO8;
    @ApiModelProperty(value = "1为批量处理标识，空或0为单个发送do")
    private String batchFlag;
}
