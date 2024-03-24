package com.soft.mapp.basecenter.domain.Wechat;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("wx_user_sync_task")
@Data
public class WxUserSyncTaskDTO {
    @ApiModelProperty(value = "微信用户同步 id")
    @TableId(value="id",type = IdType.AUTO)
    @TableField(value = "id",fill = FieldFill.INSERT)
    private Integer id;
    @ApiModelProperty(value = "微信应用ID")
    @TableField(value = "wx_appid")
    private String wxAppid;
    @ApiModelProperty(value = "同步开始时间")
    @TableField(value = "sync_start_time")
    private String syncStartTime;
    @ApiModelProperty(value = "同步结束时间")
    @TableField(value = "sync_end_time")
    private String syncEndTime;
    @ApiModelProperty(value = "计划同步总用户数")
    @TableField(value = "sync_total_num")
    private Integer syncTotalNum;
    @ApiModelProperty(value = "同步当前用户数")
    @TableField(value = "sync_local_num")
    private Integer  syncLocalNum;
    @ApiModelProperty(value = "下一个openId")
    @TableField(value = "next_openid")
    private String nextOpenid;
    @ApiModelProperty(value = "同步状态（1等待执行，2同步中，3同步完成，4同步终止）")
    @TableField(value = "sync_state")
    private String syncState;
    @ApiModelProperty(value = "同步类型（1同步用户基础信息，2断点同步用户详细信息，3全量同步用户详细信息）")
    @TableField(value = "sync_type")
    private String syncType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "最后变更时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
 
 
}
