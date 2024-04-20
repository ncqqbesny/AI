package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "货柜信息集合显示")
public class CabinetVo extends CabinetDTO  {
    @ApiModelProperty(value = "运行在线时长")
    private String  workLineTime;
}
