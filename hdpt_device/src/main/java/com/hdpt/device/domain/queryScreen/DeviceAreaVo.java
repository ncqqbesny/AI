package com.hdpt.device.domain.queryScreen;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceAreaVo {
    @ApiModelProperty(value = "用户的默认项目ID")
    private Integer mId;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String area;
    @ApiModelProperty(value = "设备数量")
    private Integer deviceNum;

}
