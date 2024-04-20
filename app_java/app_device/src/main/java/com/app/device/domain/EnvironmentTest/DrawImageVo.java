package com.app.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DrawImageVo {
    @ApiModelProperty(value = "宽")
    private Integer weight;
    @ApiModelProperty(value = "高")
    private Integer height;
    @ApiModelProperty(value = "区域")
    private ArrayList<ImageArea> areas;
}
