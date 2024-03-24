package com.hdpt.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SceneItemDeviceDataVO {
    @ApiModelProperty(value = "数据")
    private List<DeviceParamDataVO> data;
    @ApiModelProperty(value = "key数据")
    private String sceneItemName;

}
