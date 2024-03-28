package com.hdpt.device.domain.EnvironmentTest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

@Data
public class ImageArea {
    @ApiModelProperty(value = "颜色")
    private Color Color;
    @ApiModelProperty(value = "颜色")
    private Font font;
    @ApiModelProperty(value = "颜色")
    private Point StartPoint;
    @ApiModelProperty(value = "颜色")
    private Color Paint;
    @ApiModelProperty(value = "宽")
    private Integer weight;
    @ApiModelProperty(value = "高")
    private Integer height;
    @ApiModelProperty(value = "高")
    private ArrayList<DrawField> fieldList;

}
