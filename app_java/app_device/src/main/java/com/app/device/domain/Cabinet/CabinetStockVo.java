package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "货柜信息集合显示")
public class CabinetStockVo {
    @ApiModelProperty(value = "货柜信息集合列表")
    private List<CabinetDTO> CabList;
}
