package com.hdpt.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜IP显示")
public class CabinetIPVo {
    @ApiModelProperty(value = "编号")
    private Integer  ipId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "站号", required = true)
    private String  stationNo ;
    @ApiModelProperty(value = "slaveId")
    private String  slaveId;
    @ApiModelProperty(value="端口")
    private String  commName;
    @ApiModelProperty(value = "ip")
    private String  ip;
    @ApiModelProperty(value = "是否主柜（1为主柜、0从柜）")
    private Integer  isMain;
    @ApiModelProperty(value = "子网掩码")
    private String  mask;
    @ApiModelProperty(value = "网关")
    private String  gateway;
    @ApiModelProperty(value = "类型 1、WAN、2、LAN,3、服务器")
    private String  type;
    @ApiModelProperty(value = "DNS")
    private String  DNS;
    @ApiModelProperty(value = "端口")
    private String  port;

}
