package com.app.device.type;

public enum DtuCmdStatusEnum {
    send("发送了命令",0),
    rev("接收了命令",1),
    bad("通讯失败",2),
    ok("完成",3),
    ;
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private DtuCmdStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 覆盖方法
    @Override
    public String toString() {
        return  this.name;
    }
}
