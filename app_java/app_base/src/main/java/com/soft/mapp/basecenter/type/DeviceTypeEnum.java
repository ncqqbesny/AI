package com.soft.mapp.basecenter.type;

public enum DeviceTypeEnum {
    no("未分类",0),
    wifi("无线接入控制器",1),
    ap("无线AP",2),
    gateway("4G路由网关",3),
    hightGateway("5G路由网关",4),
    qjg("器具柜设备",5),
    mhq("灭火器",6),
    terminal("终端",7),
    sfx("环测网关",8)
    ;
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private DeviceTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getNameById(Integer index){
        if(index == null){
            return null;
        }
        for(DeviceTypeEnum v : values()) {
            if(v.index == index) {
                return v.name;
            }
        }
        return null;
    }
    // 覆盖方法
    @Override
    public String toString() {
        return  this.name;
    }
}
