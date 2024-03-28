package com.soft.mapp.basecenter.type;

public enum StatusEnum {
    stop("停用","0"),
    start("启用","1"),
    ;
    // 成员变量
    private String name;
    private String index;
    // 构造方法
    private StatusEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }
    public String getNameById(String index){
        if(index == null){
            return null;
        }
        for(StatusEnum v : values()) {
            if(v.index == index) {
                return v.name;
            }
        }
        return null;
    }
    public static String getValue(String name) {
        StatusEnum[]  userTypeEnums = values();
        for (StatusEnum userTypeEnum : userTypeEnums) {
            if (userTypeEnum.name.equals(name)) {
                return userTypeEnum.index;
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
