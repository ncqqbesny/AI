package com.hdpt.device.domain.SysLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class  SysLogPo {
    @ApiModelProperty(value = "id")
    private Integer logId;
    @ApiModelProperty(value = "项目编号")
    private Integer mId;
    @ApiModelProperty(value = "模块 模块内容（温度采样模块）")
    private String module;
    @ApiModelProperty(value = " 2.添加")
    private Integer type;
    @ApiModelProperty(value = "日志类型：1、标签日志，2.操作日志，3、报警日志")
    private Integer logType;
    @ApiModelProperty(value = "内容")
    private String msg;
    @ApiModelProperty(value = "操作用户")
    private String userName;
    @ApiModelProperty(value = "请求地址")
    private String url;
    @ApiModelProperty(value = "方法")
    private String method;
    @ApiModelProperty(value = "参数")
    private String param;
    @ApiModelProperty(value = "返回值")
    private String returnValue;
    @ApiModelProperty(value = "事件类型")
    private  int eventType;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "操作时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
