package com.app.device.type;

public enum DeviceTypeEnum {
    no("未分类",0),
    wifi("无线接入控制器",1),
    ap("无线AP",2),
    gateway("4G路由网关",3),
    hightGateway("5G路由网关",4),
    qjg("器具柜",5),
    mhq("灭火器",6),
    terminal("终端",7),
    sfx("环测网关",8),
    yjc("应急仓",9),
    wwj("娃娃机",10),
    ;
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private DeviceTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 覆盖方法
    @Override
    public String toString() {
        return  this.name;
    }
}
