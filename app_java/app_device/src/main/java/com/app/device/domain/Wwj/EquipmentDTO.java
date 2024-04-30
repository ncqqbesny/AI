package com.app.device.domain.Wwj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EquipmentDTO {
    @ApiModelProperty(value = "设备编号")
    private String deviceSn;
    @ApiModelProperty(value = "加分次数")
    private Integer count;
    @ApiModelProperty(value = "反馈超时等待时间")
    private Integer waitTime;
    @ApiModelProperty(value = "指令间等待间时间")
    private Integer waitMidTime;


}
