package com.app.device.domain.Wwj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EquipmentDTO {
    @ApiModelProperty(value = "设备编号")
    private String deviceSn;
    @ApiModelProperty(value = "加分次数")
    private Integer count;
    @ApiModelProperty(value = "等待")
    private Integer waitTime;

}
