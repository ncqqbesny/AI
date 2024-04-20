package com.app.device.domain.Cabinet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "货柜信息集合")
public class CabinetQueryVo {
    @ApiModelProperty(value = "id,新建默认为0")
    private Integer  cabinetId;
    @ApiModelProperty(value = "项目id,默认空，用于多项目柜号相同的处理")
    private Integer  mId;
    @ApiModelProperty(value = "货柜编号")
    private String  cabinetNo;
    @ApiModelProperty(value = "货柜名称")
    private String  cabinetName;
    @ApiModelProperty(value = "货柜编号描述")
    private String  cabdesc ;
    @ApiModelProperty(value = "货柜状态（1在线，0离线）")
    private String  cabinetStatus;
    @ApiModelProperty(value = "是否主柜（1为主柜、0从柜）")
    private Integer  isMain;
    @ApiModelProperty(value = "创建时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间:时间格式（yy-mm-dd hh:mm:ss")
    private Date  updateTime;
    @ApiModelProperty(value = "MAC地址")
    private String mac;
    @ApiModelProperty(value = "IP地址")
    private String ip;
    @ApiModelProperty(value = "子网掩码")
    private String submask;
    @ApiModelProperty(value = "网关")
    private String gateway;
    @ApiModelProperty(value = "DNS服务器")
    private String dns;
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
    @ApiModelProperty(value = "上传批次编号")
    private String  batchNo ;
}
