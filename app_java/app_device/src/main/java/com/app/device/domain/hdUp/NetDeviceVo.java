package com.app.device.domain.hdUp;

import com.app.device.utils.ExcelImport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NetDeviceVo extends NetDeviceDTO {
    @ExcelImport("编号")
    @ApiModelProperty(value = "设备编号")
    private String acDeviceSn;
    @ExcelImport("名称")
    @ApiModelProperty(value = "设备名称")
    private String acDeviceName;
    @ExcelImport("项目名称")
    @ApiModelProperty(value = "项目名称")
    private String mName;
    @ExcelImport("单位")
    @ApiModelProperty(value = "单位")
    private String acUnit;
    @ExcelImport("mac")
    @ApiModelProperty(value = "AC MAC地址")
    private String acMac;
    @ExcelImport("出货日期")
    @ApiModelProperty(value = "出货日期")
    private String outDate;
    @ExcelImport("备注")
    @ApiModelProperty(value = "备注")
    private String remark;
    @ExcelImport("地址")
    @ApiModelProperty(value = "具体地址")
    private String address;
    @ExcelImport("所在省")
    @ApiModelProperty(value = "省")
    private String province;
    @ExcelImport("所在市")
    @ApiModelProperty(value = "市")
    private String city;
    @ExcelImport("所在区")
    @ApiModelProperty(value = "区")
    private String area;
    @ExcelImport("型号")
    @ApiModelProperty(value = "设备型号")
    private String model;
    @ExcelImport("设备类型")
    @ApiModelProperty(value = "设备类型 1.无线接入控制器、2.无线AP,3.4G路由网关,4.5G路由网关，6灭火器")
    private String deviceType;
    @ApiModelProperty(value = "设备绑定时间从 yyyy-MM-dd HH:mm:ss")
    private String bindDateFrom;
    @ApiModelProperty(value = "设备绑定时间到 yyyy-MM-dd HH:mm:ss")
    private String bindDateTo;
    @ApiModelProperty(value = "设备出厂时间到 yyyy-MM-dd HH:mm:ss")
    private String outDateFrom;
    @ApiModelProperty(value = "设备出厂时间到 yyyy-MM-dd HH:mm:ss")
    private String outDateTo;
}
