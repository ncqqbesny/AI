package com.hdpt.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜设备信息集合")
public class CabinetDeviceDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  deviceId;
    @ApiModelProperty(value = "器具柜id", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "IO名称")
    private String  name ;
    @ApiModelProperty(value = "IO状态（IO状态）")
    private String  status;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
}
