package com.hdpt.device.domain.SysLog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysLogVo  extends SysLogPo  {
    @ApiModelProperty(value = "开始操作时间：时间格式（yy-mm-dd hh:mm:ss)")
    private String  operationStartTime;
    @ApiModelProperty(value = "结束操作时间：时间格式（yy-mm-dd hh:mm:ss)")
    private String operationEndTime;
    @ApiModelProperty(value = "操作时间从 yyyy-MM-dd HH:mm:ss")
    private String createDateFrom;
    @ApiModelProperty(value = "操作时间到 yyyy-MM-dd HH:mm:ss")
    private String createDateTo;



}
