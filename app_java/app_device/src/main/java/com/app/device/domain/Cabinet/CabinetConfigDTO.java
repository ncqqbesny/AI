package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜配置信息集合")
public class CabinetConfigDTO {
    @ApiModelProperty(value = "id,新建默认为0", required = true)
    private Integer configId;
    @ApiModelProperty(value = "器具柜id", required = true)
    private Integer cabinetId;
    @ApiModelProperty(value = "货柜编号", required = true)
    private String cabinetNo;
    @ApiModelProperty(value = "配置名称")
    private String name;
    @ApiModelProperty(value = "模组标识")
    private String modelSign;
    @ApiModelProperty(value = "配置值")
    private String content;
    @ApiModelProperty(value = "配置类型：1、上限阀值(温度设伐值/湿度设定值),2、下限阀值(温度设定值/湿度设定值),3、延时（排风延时/除湿延时),4、控制逻辑（0 解耦控制，1 通风优先，2 恒温优优 ，3 恒湿优先），5、运行配置（0双机运行、1混运行、2单机运行）, 6、传感器编号，8、本机DO配置,\n")
    private String configType;
    @ApiModelProperty(value = "0、PC主板,1、温湿度模组,2、冷凝除湿模组(除湿),3、通风模组(风机),4、加热模组, 14、锁,15、认证（门禁）,16、指纹头, 17、人脸识别相机, 19、大彩屏幕 ,21、RFID扫描设备（SRL5800), 31、RTU2112型通用远程扩展模块")
    private String moduleType;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date updateTime;
    @ApiModelProperty(value = "站号：对应硬件字段cabinetID柜子内部编码。特别注意，主柜为0，第一个从柜为1，以此类推。")
    private String stationNo;
    @ApiModelProperty(value = "Modbus通信的 SlaveID。就是软件上的ID 1。")
    private String slaveId;
    @ApiModelProperty(value = "地址")
    private String devAddr;
    @ApiModelProperty(value = "通信接口名字")
    private String commName;
    @ApiModelProperty(value = "通信协议")
    private String procotolType;
    @ApiModelProperty(value = "串口波特率")
    private String baudRate;
    @ApiModelProperty(value = "串口数据位")
    private String dataBits;
    @ApiModelProperty(value = "串口停止位")
    private String stopBits;
    @ApiModelProperty(value = "串口校验位")
    private String parity;
    @ApiModelProperty(value = "1表示使用,0表示非使用")
    private String devEnable;
    @ApiModelProperty(value = "天线列表")
    private String antaId;
    @ApiModelProperty(value = "设阀值(高限) 如果在温湿度类型时为温度高限")
    private String highValue;
    @ApiModelProperty(value = "设定值(低限) 如果在温湿度类型时为温度低限")
    private String lowValue;
    @ApiModelProperty(value = "设阀值(高限) 如果在温湿度类型时为湿度高限")
    private String highValue1;
    @ApiModelProperty(value = "设定值(低限) 如果在温湿度类型时为湿度高限")
    private String lowValue1;
    @ApiModelProperty(value = "传感器编号")
    private String sensorNo;
    @ApiModelProperty(value = "1：自动运行2：手动运行")
    private String  modelOperatMode;
    @ApiModelProperty(value = "关门超时分钟")
    private Integer closeTimeOut;
    @ApiModelProperty(value = "任务超时分钟")
    private Integer timeOut;
    @ApiModelProperty(value = "回退时间S 秒")
    private Integer backTimeOut;
    @ApiModelProperty(value = "锁重试次数")
    private Integer repeateTime;
    @ApiModelProperty(value = "标签发现时间time")
    private Integer scanTime;
    @ApiModelProperty(value = "运行配置（0双机运行、1混运行、2单机运行")
    private String runConfig;
    @ApiModelProperty(value = "控制逻辑 0 解耦控制，1 通风优先，2 恒温优优 ，3 恒湿优先")
    private String ctlLogic;

    @Data
    @ApiModel(value = "货柜报警信息集合")
    public static class CabinetWarningDTO {
        @ApiModelProperty(value = "id,新建默认为0",hidden = true)
        private Integer  warningId;
        @ApiModelProperty(value = "应急仓id",hidden = true)
        private Integer  cabinetId;
        @ApiModelProperty(value = "货柜编号")
        private String  cabinetNo;
        @ApiModelProperty(value = "站编号")
        private String  stationNo ;
        @ApiModelProperty(value = "信号位")
        private String signalingBit;
        @ApiModelProperty(value = "备注 信号描述",hidden = true)
        private String remark;
        @ApiModelProperty(value = "type:1、货柜离线  2、柜门超时未关闭  3、温度过高  4、货柜离线")
        private Integer  type;
        @ApiModelProperty(value = "报警值",hidden = true)
        private String  value;
        @ApiModelProperty(value = "报警时间:时间格式（yy-mm-dd hh:mm:ss",hidden = true)
        private Date warningTime;
        @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss",hidden = true)
        private Date createTime;
        @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss",hidden = true)
        private Date  updateTime;
    }
}
