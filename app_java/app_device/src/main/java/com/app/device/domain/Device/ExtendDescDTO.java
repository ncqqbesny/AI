package com.app.device.domain.Device;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ExtendDescDTO {
    @ApiModelProperty(value = "主键")
    private String 	gid                ;
    @ApiModelProperty(value = "项目ID")
    private Integer	mId                ;
    @ApiModelProperty(value = "设备分类id")
    private String 	deviceTypeGid      ;
    @ApiModelProperty(value = "设备分类编码")
    private String 	deviceTypeCode     ;
    @ApiModelProperty(value = "业务数据标识")
    private String 	extDataModule      ;
    @ApiModelProperty(value = "明细层级")
    private Integer detailLevel        ;
    @ApiModelProperty(value = "描述模版版本")
    private String 	descVer            ;
    @ApiModelProperty(value = "设备分类目录Code")
    private String 	catalogCode        ;
    @ApiModelProperty(value = "描述模版编码Code")
    private String	descCode           ;
    @ApiModelProperty(value = "采集方式")
    private Integer	collectMode        ;
    @ApiModelProperty(value = "字段长度")
    private Integer	fieldLength        ;
    @ApiModelProperty(value = "扩展字段名称")
    private String 	extFieldCode       ;
    @ApiModelProperty(value = "数据字段显示名称")
    private String 	fieldName          ;
    @ApiModelProperty(value = "数据字段名称")
    private String 	fieldCode          ;
    @ApiModelProperty(value = "字段类型")
    private String 	fieldType          ;
    @ApiModelProperty(value = "是否唯一")
    private Integer isUnique           ;
    @ApiModelProperty(value = "是否必录入")
    private Integer isMust             ;
    @ApiModelProperty(value = "是否为查询条件")
    private Integer	isSearch           ;
    @ApiModelProperty(value = "是否为查询结果")
    private String 	isResult           ;
    @ApiModelProperty(value = "查询条件控件排序")
    private Integer searchOrder        ;
    @ApiModelProperty(value = "查询结果排序")
    private Integer resultOrder        ;
    @ApiModelProperty(value = "是否是区间时间")
    private Integer isRangeTime        ;
    @ApiModelProperty(value = "删除标识")
    private Integer isDeleted          ;
    @ApiModelProperty(value = "表格列宽度")
    private Integer	gridWidth          ;
    @ApiModelProperty(value = "备注")
    private String 	note               ;
    @ApiModelProperty(value = "字段显示规则")
    private String 	fieldDsplyRule     ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  lastTime          ;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;  ;
    @ApiModelProperty(value = "国标编码")
    private String 	gbCode             ;
}
