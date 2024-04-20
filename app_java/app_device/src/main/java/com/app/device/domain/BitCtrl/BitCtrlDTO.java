package com.app.device.domain.BitCtrl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "设备点位配置")
public class BitCtrlDTO {
    @ApiModelProperty(value = "id,新建,查询默认为0 ，保存必填项")
    private Integer ctrlId;
    @ApiModelProperty(value = "请求编号")
    private String requestNo;
    @ApiModelProperty(value = "请求处理标志：0、默认不处理，1、未处理（需处理），2、处理中，3，处理完成")
    private Integer requestFlag;
    @ApiModelProperty(value = "设备编号--序列号 必填项")
    private String deviceSn;
    @ApiModelProperty(value = "调用编号，保存必填项")
    private String ctrlNo;
    @ApiModelProperty(value = "状态：0、禁用（停止操作），1、启用（开启操作） 保存必填项")
    private Integer status;
    @ApiModelProperty(value = "货柜编号  保存必填项")
    private String  cabinetNo;
    @ApiModelProperty(value = "站号  保存必填项 ")
    private String  stationNo;
    @ApiModelProperty(value = "信号类型：0、ON，1、模拟量 2.OFF")
    private Integer type;
    @ApiModelProperty(value = "信号类型type=1时模拟量存在的值")
    private String analogNum;
    @ApiModelProperty(value = "控制方式 单位秒")
    private Integer ctrlMode;
    @ApiModelProperty(value = "硬件地址类型 0、PC主板,1、温湿度模组,2、冷凝除湿模组(除湿),3、通风模组(风机),4、加热模组, 14、锁,15、认证（门禁）,16、指纹头, 17、人脸识别相机, 19、大彩屏幕 ,21、RFID扫描设备（SRL5800), 31、RTU2112型通用远程扩展模块,32.GCU,33.TM")
    private Integer addressType;
    @ApiModelProperty(value = "硬件地址")
    private Integer address;
    @ApiModelProperty(value = "功能说明：（1、投入、2、自动、3、闭合、4、切断 5、开锁、6）")
    private String typeDesc;
    @ApiModelProperty(value = "控制类型 0.DO,1.DI 2.温度，3湿度")
    private Integer ctrlType;
    @ApiModelProperty(value = "控制数字，例如1=DI1,2=net_deviceDO2")
    private Integer ctrlNum;
    @ApiModelProperty(value = "信号位")
    private String signalingBit;
    @ApiModelProperty(value = "端口")
    private String port;
    @ApiModelProperty(value = "备注 信号描述")
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date updateTime;
}
