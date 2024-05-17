package com.app.device.domain.HardWare;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作硬件的参数
 */
@Data
public class OperHardDTO {
    @ApiModelProperty(value = "请求编号,唯一码")
    private String  reqNo;
    @ApiModelProperty(value = "设备编号")
    private String deviceSn;
    @ApiModelProperty(value = "操作开关次数")
    private Integer count;
}
