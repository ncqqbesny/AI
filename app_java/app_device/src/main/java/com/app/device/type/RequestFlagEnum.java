package com.app.device.type;

public enum RequestFlagEnum {
    Not(0,"默认不处理"),
    start(1,"即时处理"),
    closeLock(2,"关锁延迟处理"),
    batch(3,"批量处理"),
    end(4,"处理完成"),
    auto(5,"自动逻辑处理"),
    ;
    RequestFlagEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;
    //通过ID获取枚举值
    public static RequestFlagEnum getById(Integer id) {
        if(id == null){
            return null;
        }
        for(RequestFlagEnum v : values()) {
            if(v.id == id) {
                return v;
            }
        }
        return null;
    }
}
