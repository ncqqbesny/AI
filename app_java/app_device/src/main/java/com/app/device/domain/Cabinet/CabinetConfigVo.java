package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜配置")
public class CabinetConfigVo {
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "站号 四组柜子为一个站，默认为1" , required = true)
    private String  stationNo;
    @ApiModelProperty(value = "温度设定值")
    private String temperatureHSet         ;
    @ApiModelProperty(value = "湿度设定值")
    private String humidityHSet           ;
    @ApiModelProperty(value = "温度阀值")
    private String temperatureSet      ;
    @ApiModelProperty(value = "湿度阀值")
    private String humiditySet         ;
    @ApiModelProperty(value = "报警温度")
    private String warningTemperature      ;
    @ApiModelProperty(value = "报警湿度")
    private String warningHumidity;
    @ApiModelProperty(value = "报警温度低限")
    private String warningTemperatureLow      ;
    @ApiModelProperty(value = "报警湿度低限")
    private String warningHumidityLow;
    @ApiModelProperty(value = "锁舌回退时间（秒）")
    private String lockBackTime;
    @ApiModelProperty(value = "锁闭尝试（次）")
    private String lockCloseTry;
    @ApiModelProperty(value = "标签发现")
    private String labelDiscovery;
    @ApiModelProperty(value = "排风延时  ")
    private String dischargeAirTimeOut ;
    @ApiModelProperty(value = "除湿延时  ")
    private String dehumidifyTimeOut   ;
    @ApiModelProperty(value = "控制逻辑 0 解耦控制，1 通风优先，2 恒温优优 ，3 恒湿优先 ")
    private String ctlLogic            ;
    @ApiModelProperty(value = "运行配置  0双机运行、1混运行、2单机运行")
    private String runConfig           ;
    @ApiModelProperty(value = "控制操作 1自动、2遥控（手工运行） ")
    private String ctlOperation        ;
    @ApiModelProperty(value = "传感器编号")
    private String sensorNo            ;
    @ApiModelProperty(value = "任务超时(秒) ")
    private String openTimeOut    ;
    @ApiModelProperty(value = "延迟关锁(秒)")
    private String autoLockTime    ;
    @ApiModelProperty(value = "通风状态：1、打开，2，关闭")
    private String  ventilation;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidification;

    /*@ApiModelProperty(value = "除湿控制  1、风扇打开，2，风扇关闭")
    private String dehumidifyCtrl ;
    @ApiModelProperty(value = "除湿控制1 1、风扇打开，2，风扇关闭 ")
    private String dehumidifyCtrl1      ;
    @ApiModelProperty(value = "除湿控制  1，加热打开，2、加热关闭。 ")
    private String dehumidifyHeatCtrl      ;
    @ApiModelProperty(value = "除湿控制1 1，加热打开，2、加热关闭。 ")
    private String dehumidifyHeatCtrl1      ;
    */


}
