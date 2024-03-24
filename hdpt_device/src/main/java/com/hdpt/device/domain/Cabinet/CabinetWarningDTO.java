package com.hdpt.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜报警信息集合")
public class CabinetWarningDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer  warningId;
    @ApiModelProperty(value = "器具柜id", required = true)
    private Integer  cabinetId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo;
    @ApiModelProperty(value = "站编号")
    private String  stationNo ;
    @ApiModelProperty(value = "type:1、货柜离线  2、柜门超时未关闭  3、温度过高  4、货柜离线", required = true)
    private Integer  type;
    @ApiModelProperty(value = "报警值")
    private String  value;
    @ApiModelProperty(value = "报警时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date warningTime;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
}
