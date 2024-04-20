package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜设备打开")
public class CabinetOpenVo {
    @ApiModelProperty(value = "货柜编号", required = true)
    private String  cabinetNo       ;
    @ApiModelProperty(value = "站号 四组柜子为一个站", required = true)
    private String  stationNo;
    @ApiModelProperty(value = "指令 1、打开、2关闭")
    private String  controlCommand     ;
}
