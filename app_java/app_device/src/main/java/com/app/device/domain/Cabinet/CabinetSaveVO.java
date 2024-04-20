package com.app.device.domain.Cabinet;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜基础信息集合")
public class CabinetSaveVO  {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "项目id,默认空，用于多项目柜号相同的处理")
    private Integer  mId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "货柜名称")
    private String  cabinetName;
    @ApiModelProperty(value = "站号 站号硬件配置cabinetID 子内部编码。特别注意，主柜为0，第一个从柜为1，以此类推。")
    private String  stationNo;
    @ApiModelProperty(value = "柜组编号")
    private String  batchNo ;
    @ApiModelProperty(value = "硬件Modbus通信的 SlaveID。就是软件上的ID 1")
    private String  slaveId ;
    @ApiModelProperty(value = "货柜编号描述")
    private String  cabdesc ;
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
    @ApiModelProperty(value = "货柜状态（1启用，0停用）", required = true)
    private String  cabinetStatus;
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
    @ApiModelProperty(value = "除湿状态：1、打开，2，关闭")
    private String  dehumidification;
    @ApiModelProperty(value = "柜灯状态：1、打开，2，关闭")
    private String  light;
    @ApiModelProperty(value = "运行时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date runTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
    @ApiModelProperty(value = "MAC地址")
    private String mac;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @ApiModelProperty(value = "子网掩码")
    private String submask;
    @ApiModelProperty(value = "网关")
    private String gateway;
    @ApiModelProperty(value = "DNS服务器")
    private String dns;
}
