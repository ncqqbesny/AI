package com.app.device.type;

/**
 * 操作类型
 */
public enum OpTypeEnum {
    op("操作",0),
    re("接收",1),
    manual ("手工操作",2),
    ;
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private OpTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 覆盖方法
    @Override
    public String toString() {
        return  this.name;
    }
}
