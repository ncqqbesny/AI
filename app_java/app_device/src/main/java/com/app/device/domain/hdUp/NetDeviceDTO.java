package com.app.device.domain.hdUp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class NetDeviceDTO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "父id")
    private Integer parentId;
    @ApiModelProperty(value = "项目ID")
    private Integer mId;
    @ApiModelProperty(value = "设备编号")
    private String acDeviceSn;
    @ApiModelProperty(value = "设备名称")
    private String acDeviceName;
    @ApiModelProperty(value = "位置显示设备名")
    private String sortorderDeviceName;
    @ApiModelProperty(value = "显示设备名")
    private String displayDeviceName;
    @ApiModelProperty(value = "设备型号")
    private String model;
    @ApiModelProperty(value = "单位")
    private String acUnit;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String area;
    @ApiModelProperty(value = "具体地址")
    private String address;
    @ApiModelProperty(value = "状态（1在线,0离线)")
    private String status;
    @ApiModelProperty(value = "上传信息次数")
    private Integer upNum;
    @ApiModelProperty(value = "远程连接地址")
    private String url;
    @ApiModelProperty(value = "AC硬件版本号")
    private String acDeviceHw;
    @ApiModelProperty(value = "AC软件版本号")
    private String acDeviceSw;
    @ApiModelProperty(value = "AC MAC地址")
    private String acMac;
    @ApiModelProperty(value = "拨号方式/取值：网络管理-internet配置。为三项中的一项“DHCP”或“PPPoE”或“Static”")
    private String acDialMode;
    @ApiModelProperty(value = "WAN接口IP地址")
    private String acIp;
    @ApiModelProperty(value = "WAN接口子网掩码")
    private String acSubmask;
    @ApiModelProperty(value = "WAN接口网关")
    private String acGateway;
    @ApiModelProperty(value = "WAN接口DNS服务器")
    private String acDns;
    @ApiModelProperty(value = "AC运行当前时间和日期")
    private String acCurrentTime;
    @ApiModelProperty(value = "AC运行的时长")
    private String acRuntime;
    @ApiModelProperty(value = "设备时区，格式GMT+数字")
    private String acTimeZone;
    @ApiModelProperty(value = "在线的AP数量")
    private String acApOnlineNum;
    @ApiModelProperty(value = "是否主柜（1为主柜、0从柜）")
    private Integer  isMain;
    @ApiModelProperty(value = "在线的用户数量")
    private String acUserOnlineNum;
    @ApiModelProperty(value = "在线AP详情")
    private String acOnlineApInfo;

    @ApiModelProperty(value = "ssid信息")
    private String apSsid;
    @ApiModelProperty(value = "ap天线信息")
    private String apRadio;
    @ApiModelProperty(value = "连接AP设备IP地址表")
    private String apIpList;
    @ApiModelProperty(value = "AP注册到AC的时间和日期")
    private Date apRuntime;
    @ApiModelProperty(value = "AP系统时间和日期")
    private Date apSysTime;
    @ApiModelProperty(value = "报警、故障信息license错误信息两个取值Non或license err")
    private String apLicenseError;

    @ApiModelProperty(value = "显示顺序")
    private Integer displaySortorder;
    @ApiModelProperty(value = "连接状态（信号强度")
    private Float staSignal;
    @ApiModelProperty(value = "连接时间")
    private Date staRuntime;
    @ApiModelProperty(value = "上下行数据速率(发送)")
    private String staDataTxRate;
    @ApiModelProperty(value = "上下行数据速率(接收)")
    private String staDataRxRate;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "状态信息发送间隔（秒）")
    private Integer interval;
    @ApiModelProperty(value = "设备数量")
    private Integer count;
    @ApiModelProperty(value = "上传的json字符串")
    private String upJson;
    @ApiModelProperty(value = "设备类型 1.无线接入控制器、2.4G路由网关,3.5G路由网关")
    private String deviceType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "出厂时间")
    private Date outTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "绑定设备时间")
    private Date bindTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
