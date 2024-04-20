package com.app.device.domain.Device;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
@TableName("t_device")
@Data
public class DeviceDTO {
    @ApiModelProperty(value = "gid")
    @TableId(value="GID",type = IdType.ASSIGN_UUID)
    @TableField(value = "GID",fill = FieldFill.INSERT)
    private String gid;
    @ApiModelProperty(value = "设备分类CODE,必填项")
    @TableField(value = "deviceTypeCode")
    private String deviceTypeCode;
    @ApiModelProperty(value = "设备分类目录CODE")
    @TableField(value = "catalogCode")
    private String catalogCode;
    @ApiModelProperty(value = "模版编码CODE")
    @TableField(value = "descCode")
    private String descCode;
    @ApiModelProperty(value = "设备业务类型编码")
    @TableField(value = "devicetagCode")
    private String devicetagCode;
    @ApiModelProperty(value = "系统来源")
    @TableField(value = "systemCode")
    private String systemCode;
    @ApiModelProperty(value = "设备名称")
    @TableField(value = "deviceName")
    private String deviceName;
    @ApiModelProperty(value = "设备编号--序列号")
    @TableField(value = "deviceSn")
    private String deviceSn;
    @ApiModelProperty(value = "显示设备名")
    @TableField(value = "displayDeviceName")
    private String displayDeviceName;
    @ApiModelProperty(value = "位置显示设备名")
    @TableField(value = "sortorderDeviceName")
    private String sortorderDeviceName;
    @ApiModelProperty(value = "显示顺序,安装位置")
    @TableField(value = "displaySortorder")
    private String displaySortorder;
    @ApiModelProperty(value = "设备业务操作人员代码")
    @TableField(value = "userId")
    private String userId;
    @ApiModelProperty(value = "设备业务操作人员名称")
    @TableField(value = "userName")
    private String userName;
    @ApiModelProperty(value = "经度")
    @TableField(value = "longitude")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    @TableField(value = "latitude")
    private String latitude;
    @ApiModelProperty(value = "省")
    @TableField(value = "province")
    private String province;
    @ApiModelProperty(value = "市")
    @TableField(value = "city")
    private String city;
    @ApiModelProperty(value = "区")
    @TableField(value = "area")
    private String area;
    @ApiModelProperty(value = "具体地址")
    @TableField(value = "address")
    private String address;
    @ApiModelProperty(value = "状态（1在线,0离线)")
    @TableField(value = "status")
    private String status;
    @ApiModelProperty(value = "业务发生时间")
    @TableField(value = "opHappTm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date opHappTm;
    @ApiModelProperty(value = "项目ID")
    @TableField(value = "mid")
    private Integer mid;
    @ApiModelProperty(value = "项目名称")
    @TableField(value = "mName")
    private String mName;
    @ApiModelProperty(value = "硬件版本号")
    @TableField(value = "deviceHw")
    private String deviceHw;
    @ApiModelProperty(value = "软件版本号")
    @TableField(value = "deviceSw")
    private String deviceSw;
    @ApiModelProperty(value = "MAC地址")
    @TableField(value = "mac")
    private String mac;
    @ApiModelProperty(value = "IP地址")
    @TableField(value = "ip")
    private String ip;
    @ApiModelProperty(value = "子网掩码")
    @TableField(value = "submask")
    private String submask;
    @ApiModelProperty(value = "网关")
    @TableField(value = "gateway")
    private String gateway;
    @ApiModelProperty(value = "DNS服务器")
    @TableField(value = "dns")
    private String  dns;
    @ApiModelProperty(value = "运行当前时间和日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @TableField(value = "`CURRENT_TIME`")
    private Date currentTime;
    @ApiModelProperty(value = "运行的时长")
    @TableField(value = "runTime")
    private String runTime;
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;
    @ApiModelProperty(value = "实际状态信息发送间隔（秒）")
    @TableField(value = "`interval`")
    private String interval;
    @ApiModelProperty(value = "上传的json字符串")
    @TableField(value = "upJson")
    private String upJson;
    @ApiModelProperty(value = "绑定设备时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @TableField(value = "bindTime")
    private Date bindTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "出厂时间")
    @TableField(value = "outTime")
    private Date outTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "LAST_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date lastTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
