package com.hdpt.device.domain.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageRecordModel {

    @ApiModelProperty(value = "交换器")
    private String exchange;
    @ApiModelProperty(value = "key")
    private String routionKey;
    @ApiModelProperty(value = "内容")
    private String messageContent;

}
