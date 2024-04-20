package com.app.device.domain.Cabinet;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜设备信息集合")
public class CabinetDeviceDTO {
    @ApiModelProperty(value = "id,新建默认为0")
    private Integer  deviceId;
    @ApiModelProperty(value = "应急仓id")
    private Integer  cabinetId;
    @ApiModelProperty(value = "货柜编号（必填）")
    private String  cabinetNo;
    @ApiModelProperty(value = "站号：对应硬件字段cabinetID柜子内部编码。特别注意，主柜为0，第一个从柜为1，以此类推。")
    private String stationNo;
    @ApiModelProperty(value = "设备名称")
    private String  name ;
    @ApiModelProperty(value = "设备状态，1、开启、0、关闭")
    private Integer  status;
    @ApiModelProperty(value = "设备编号")
    private String	code;
    @ApiModelProperty(value = "型号")
    private String	model;
    @ApiModelProperty(value = "设备描述")
    private String	desc;
    @ApiModelProperty(value = "经度")
    private Double	longitude;
    @ApiModelProperty(value = "纬度")
    private Double	latitude;
    @ApiModelProperty(value = "省")
    private String	province;
    @ApiModelProperty(value = "市")
    private String	city;
    @ApiModelProperty(value = "区")
    private String	area;
    @ApiModelProperty(value = "具体地址")
    private String	address;
    @ApiModelProperty(value = "二维码地址")
    private String	url;
    @ApiModelProperty(value = "箱长mm")
    private Integer	length;
    @ApiModelProperty(value = "箱宽mm")
    private Integer	width;
    @ApiModelProperty(value = "箱高mm")
    private Integer	hight;
    @ApiModelProperty(value = "自重kg")
    private Integer	selfWeight;
    @ApiModelProperty(value = "承重kg")
    private Integer	supportWeight;
    @ApiModelProperty(value = "备注")
    private String	remark;
    @ApiModelProperty(value = "硬件版本号")
    private String	deviceHw;
    @ApiModelProperty(value = "软件版本号")
    private String	deviceSw;
    @ApiModelProperty(value = "上传版本号")
    private String	opHw;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
