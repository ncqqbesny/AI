package com.app.device.domain.Device;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@TableName("t_device_type")
@Data
public class DeviceTypeDTO {
    @ApiModelProperty(value = "主键")
    @TableId(value="GID",type = IdType.ASSIGN_UUID)
    @TableField(value = "GID",fill = FieldFill.INSERT)
    private String gid           ;
    @ApiModelProperty(value = "设备类型名称")
    @TableField(value = "deviceTypeName")
    private String deviceTypeName;
    @ApiModelProperty(value = "分类编码")
    @TableField(value = "typeCode")
    private String typeCode      ;
    @ApiModelProperty(value = "父级分类Gid")
    @TableField(value = "parentGid")
    private String parentGid     ;
    @ApiModelProperty(value = "父级分类Code")
    @TableField(value = "parentCode")
    private String parentCode    ;
    @ApiModelProperty(value = "项目代码")
    @TableField(value = "mid")
    private Integer mid           ;
    @ApiModelProperty(value = "设备数")
    @TableField(value = "deviceCount")
    private Integer deviceCount   ;
    @ApiModelProperty(value = "存储空间")
    @TableField(value = "tablePrefix")
    private String tablePrefix   ;
    @ApiModelProperty(value = "排序")
    @TableField(value = "typeOrder")
    private String typeOrder     ;
    @ApiModelProperty(value = "启用状态1、启用，0停用")
    @TableField(value = "enabledStatus")
    private Integer enabledStatus ;
    @ApiModelProperty(value = "编码前缀")
    @TableField(value = "codingPrefix")
    private String codingPrefix  ;
    @ApiModelProperty(value = "节点属性")
    @TableField(value = "nodeAttr")
    private Integer nodeAttr      ;
    @ApiModelProperty(value = "节点层次")
    @TableField(value = "nodeLevel")
    private Integer nodeLevel     ;
    @ApiModelProperty(value = "启用时间")
    @TableField(value = "enabledTm")
    private String enabledTm     ;
    @ApiModelProperty(value = "分类号")
    @TableField(value = "classNum")
    private String classNum      ;
    @ApiModelProperty(value = "是否为主设备 1、是，0 否")
    @TableField(value = "ismainDevice")
    private Integer ismainDevice  ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "LAST_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date lastTime          ;
    @NotNull(message = "创建时间不能为空")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date 	createTime;

}
