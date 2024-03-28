package com.hdpt.device.domain.Device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeviceVo extends DeviceDTO {
    @ApiModelProperty(value = "设备扩展信息")
    Map<String, Object> deviceExtends;
    @ApiModelProperty(value = "设备gid集合")
    List<String> gids;
    @ApiModelProperty(value = "项目id集合")
    List<String> mids;
    @ApiModelProperty(value = "设备分类CODE集合")
    List<String> deviceTypeCodes;
    @ApiModelProperty(value = "是否job",hidden = true)
    boolean isJob;
}