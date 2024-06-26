package com.app.device.domain.Device;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @ApiModelProperty(value = "发送控制信息")
    private String msg;
}