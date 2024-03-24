package com.hdpt.device.domain.loginVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Menu {
    private Integer menuId ;
    private String menuName ;
    private Integer parentMenuId;
    private String type;
    private String staticRouter;
    private String remark;
    private String skipUrl;
    private Integer orderNum;
    private String isOutUrl;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
