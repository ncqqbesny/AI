package com.hdpt.device.domain.queryScreen;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceType {
    @ApiModelProperty(value = "用户的默认项目ID")
    private Integer mId;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "设备数量")
    private Integer deviceNum;

}
