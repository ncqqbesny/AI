package com.app.device.domain.Wwj;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_dtu_cmd")
@Data
public class DtuCmdPO {
    @ApiModelProperty(value = "gid")
    @TableId(value="GID",type = IdType.ASSIGN_UUID)
    @TableField(value = "GID",fill = FieldFill.INSERT)
    private String gid;

    @ApiModelProperty(value = "单号")
    @TableField(value = "cmd_no")
    private String cmdNo;
    @ApiModelProperty(value = "请求单号")
    @TableField(value = "req_no")
    private String reqNo;
    @ApiModelProperty(value = "设备编号")
    @TableField(value = "device_sn")
    private String deviceSn;
    @ApiModelProperty(value = "项目id")
    @TableField(value = "m_id")
    private Integer mId;
    @ApiModelProperty(value = "用户id")
    @TableField(value = "user_id")
    private Integer userId;
    @ApiModelProperty(value = "状态（1在线,0离线)")
    @TableField(value = "status")
    private Integer status;
    @ApiModelProperty(value = "发送命令时间")
    @TableField(value = "send_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date sendTime;
    @ApiModelProperty(value = "发送执行命令")
    @TableField(value = "send_cmd")
    private String sendCmd;
    @ApiModelProperty(value = "发送地址")
    @TableField(value = "send_url")
    private String sendUrl;
    @ApiModelProperty(value = "命令描述")
    @TableField(value = "cmd_desc")
    private String cmdDesc;
    @ApiModelProperty(value = "接收命令")
    @TableField(value = "rev_cmd")
    private String revCmd;
    @ApiModelProperty(value = "接收地址")
    @TableField(value = "rev_url")
    private String revUrl;
    @ApiModelProperty(value = "接收命令时间")
    @TableField(value = "rev_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date revTime;
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;
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
