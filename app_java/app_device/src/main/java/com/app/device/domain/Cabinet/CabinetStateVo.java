package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜状态显示")
public class CabinetStateVo {
    @ApiModelProperty(value = "货柜编号")
    private String  cabinetNo       ;
    @ApiModelProperty(value = "温度")
    private String  temperature     ;
    @ApiModelProperty(value = "湿度")
    private String  humidity        ;
    @ApiModelProperty(value = "柜门")
    private String  door            ;
    @ApiModelProperty(value = "柜锁")
    private String  lock            ;
    @ApiModelProperty(value = "物资")
    private Integer matterNum       ;
    @ApiModelProperty(value = "通风")
    private String  ventilation     ;
    @ApiModelProperty(value = "除湿")
    private String  dehumidification;
}
