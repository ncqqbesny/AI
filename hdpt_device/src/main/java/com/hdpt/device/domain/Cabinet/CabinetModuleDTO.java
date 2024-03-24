package com.hdpt.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜模块信息集合")
public class CabinetModuleDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  moduleId;
    @ApiModelProperty(value = "模块地址(例如1-01)")
    private String  address;
    @ApiModelProperty(value = "器具柜id", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "站号", required = true)
    private String  stationNo;
    @ApiModelProperty(value = "模块名称")
    private String  name ;
    @ApiModelProperty(value = "内容信息(主要是备注信息例如DO,DI）等")
    private String  content;
    @ApiModelProperty(value = "模组类型，0、PC主板,1、温湿度模组,2、冷凝除湿模组(除湿),3、通风模组(风机),4、加热模组, 14、锁,15、认证（门禁）,16、指纹头, 17、人脸识别相机, 19、大彩屏幕 ,21、RFID扫描设备（SRL5800), 31、RTU2112型通用远程扩展模块", required = true)
    private String  moduleType;
    @ApiModelProperty(value = "1:正常2：离线 3：运行4：关闭（state)", required = true)
    private String  state;
    @ApiModelProperty(value = "1：自动运行2：手动运行")
    private String  modelOperatMode;
    @ApiModelProperty(value = "模组标识")
    private String  modelSign ;
    @ApiModelProperty(value = "端口")
    private String  port ;
    @ApiModelProperty(value="运行配置（0双机运行、1混运行、2单机运行")
    private String  runConfig ;
    private String  DO1;
    private String  DO2;
    private String  DO3;
    private String  DO4;
    private String  DO5;
    private String  DO6;
    private String  DO7;
    private String  DO8;
    private String  DI1;
    private String  DI2;
    private String  DI3;
    private String  DI4;
    private String  DI5;
    private String  DI6;
    private String  DI7;
    private String  DI8;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
}
