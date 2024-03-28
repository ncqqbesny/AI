package com.hdpt.device.domain.queryScreen;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceNameVo {
    @ApiModelProperty(value = "用户的默认项目ID")
    private Integer mId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "设备位置")
    private String area;
    @ApiModelProperty(value = "设备状态，1为在线，0为离线")
    private String status;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date updateTime;

}
