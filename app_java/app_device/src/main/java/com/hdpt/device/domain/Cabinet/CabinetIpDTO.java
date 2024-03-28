package com.hdpt.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜IP信息集合")
public class CabinetIpDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  ipId;
    @ApiModelProperty(value = "器具柜id", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "站编号")
    private String  stationNo ;
    @ApiModelProperty(value = "ip", required = true)
    private String  ip;
    @ApiModelProperty(value = "子网掩码", required = true)
    private String  mask;
    @ApiModelProperty(value = "类型:1、WAN、2、LAN 3,硬件服务器 ", required = true)
    private String  type;
    @ApiModelProperty(value = "网关")
    private String  gateway;
    @ApiModelProperty(value = "dns")
    private String  dns ;
    @ApiModelProperty(value = "端口")
    private String  port ;
    @ApiModelProperty(value = "配置json")
    private String  configJson ;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
}
