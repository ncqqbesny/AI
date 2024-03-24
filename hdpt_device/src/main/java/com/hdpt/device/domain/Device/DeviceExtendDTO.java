package com.hdpt.device.domain.Device;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@TableName("t_device_extend")
@Data
public class DeviceExtendDTO {
    @TableId(value="GID",type = IdType.ASSIGN_UUID)
    @TableField(value = "GID",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "主键")
    private String gid            ;
    @ApiModelProperty(value = "设备基础数据ID")
    @TableField(value = "DEVICEGID")
    private String deviceGid      ;
    @TableField(value = "DEVICETYPECODE")
    @ApiModelProperty(value = "设备分类CODE")
    private String deviceTypeCode ;
    @TableField(value = "CATALOGCODE")
    @ApiModelProperty(value = "设备分类目录CODE")
    private String catalogCode    ;
    @TableField(value = "DESCCODE")
    @ApiModelProperty(value = "模版编码CODE")
    private String descCode       ;
    @TableField(value = "MID")
    @ApiModelProperty(value = "项目ID")
    private Integer mid            ;
    private String ext01          ;
    private String ext02          ;
    private String ext03          ;
    private String ext04          ;
    private String ext05          ;
    private String ext06          ;
    private String ext07          ;
    private String ext08          ;
    private String ext09          ;
    private String ext10          ;
    private String ext11          ;
    private String ext12          ;
    private String ext13          ;
    private String ext14          ;
    private String ext15          ;
    private String ext16          ;
    private String ext17          ;
    private String ext18          ;
    private String ext19          ;
    private String ext20          ;
    private String ext21          ;
    private String ext22          ;
    private String ext23          ;
    private String ext24          ;
    private String ext25          ;
    private String ext26          ;
    private String ext27          ;
    private String ext28          ;
    private String ext29          ;
    private String ext30          ;
    private String ext31          ;
    private String ext32          ;
    private String ext33          ;
    private String ext34          ;
    private String ext35          ;
    private String ext36          ;
    private String ext37          ;
    private String ext38          ;
    private String ext39          ;
    private String ext40          ;
    private String ext41          ;
    private String ext42          ;
    private String ext43          ;
    private String ext44          ;
    private String ext45          ;
    private String ext46          ;
    private String ext47          ;
    private String ext48          ;
    private String ext49          ;
    private String ext50          ;
    private String ext51          ;
    private String ext52          ;
    private String ext53          ;
    private String ext54          ;
    private String ext55          ;
    private String ext56          ;
    private String ext57          ;
    private String ext58          ;
    private String ext59          ;
    private String ext60          ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "LAST_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date    lastTime          ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    @NotNull(message = "创建时间不能为空")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
