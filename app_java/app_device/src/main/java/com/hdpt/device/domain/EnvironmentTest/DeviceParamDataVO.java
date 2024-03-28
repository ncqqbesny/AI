package com.hdpt.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceParamDataVO {
    @ApiModelProperty(value = "参数名")
    private String paramName;
    @ApiModelProperty(value = "数据")
    private String data;

}
