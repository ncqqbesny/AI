package com.app.device.domain.Cabinet;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜基础信息集合")
public class CabinetDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "项目id,默认空，用于多项目柜号相同的处理")
    private Integer  mId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "货柜名称")
    private String  cabinetName;
    @ApiModelProperty(value = "站号 四组柜子为一个站", required = true)
    private String  stationNo;
    @ApiModelProperty(value = "硬件Modbus通信的 SlaveID。就是软件上的ID 1")
    private String  slaveId ;
    @ApiModelProperty(value = "柜组编号")
    private String  batchNo ;
    @ApiModelProperty(value = "货柜编号描述")
    private String  cabdesc ;
    @ApiModelProperty(value = "货柜状态（1启用，0停用）", required = true)
    private String  cabinetStatus;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String area;
    @ApiModelProperty(value = "具体地址")
    private String address;
    @ApiModelProperty(value = "是否主柜（1为主柜、0从柜）", required = true)
    private Integer  isMain;
    @ApiModelProperty(value = "柜门状诚1、打开，2，关闭")
    private Integer  openStauts;
    @ApiModelProperty(value = "温度")
    private String  temperature;
    @ApiModelProperty(value = "湿度")
    private String  humidity;
    @ApiModelProperty(value = "柜锁状态：1、打开，2，关闭")
    private String  lock;
    @ApiModelProperty(value = "物资数量")
    private Integer  matterNum;
    @ApiModelProperty(value = "锁磁状态:1:开启2：关闭")
    private String  stateCantact;
    @ApiModelProperty(value = "柜锁状态:1:开启2：关闭")
    private String  stateBolt;
    @ApiModelProperty(value = "通风状态：1、打开，2，关闭")
    private String  ventilation;
    @ApiModelProperty(value = "通风状态风扇1：1、打开，2，关闭")
    private String  ventilationFan1;
    @ApiModelProperty(value = "通风状态风扇2：1、打开，2，关闭")
    private String  ventilationFan2;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidification;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidificationFan1;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidificationFan2;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidificationHot1;
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidificationHot2;
    @ApiModelProperty(value = "柜灯状态：1、打开，2，关闭")
    private String  light;
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
    private String sensorNo      ;
    @ApiModelProperty(value = "版本号")
    private String version      ;
    @ApiModelProperty(value = "任务超时(秒) ")
    private String openTimeOut    ;
    @ApiModelProperty(value = "延迟关锁(秒)")
    private String autoLockTime    ;
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
    @ApiModelProperty(value = "运行时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date runTime;
    @ApiModelProperty(value = "MAC地址")
    private String mac;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @ApiModelProperty(value = "子网掩码")
    private String submask;
    @ApiModelProperty(value = "网关")
    private String gateway;
    @ApiModelProperty(value = "DNS服务器")
    private String dns;
    @ApiModelProperty(value = "设备数量")
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
}
