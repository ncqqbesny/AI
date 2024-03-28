package com.soft.mapp.basecenter.domain.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class OrdersVo extends OrdersPo {
    @ApiModelProperty(value = "子项目")
    private List<OrdersPo> children;
}
