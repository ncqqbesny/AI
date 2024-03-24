package com.soft.mapp.basecenter.type;

public enum UserTypeEnum {
    web("网页注册用户","1"),
    smallPrg("小程序注册用户","2"),
    publicNo("公从号注册用户","3"),
    ;
    // 成员变量
    private String name;
    private String index;
    // 构造方法
    private UserTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }
    public String getNameById(String index){
        if(index == null){
            return null;
        }
        for(UserTypeEnum v : values()) {
            if(v.index == index) {
                return v.name;
            }
        }
        return null;
    }
    public static String getValue(String name) {
        UserTypeEnum[]  userTypeEnums = values();
        for (UserTypeEnum userTypeEnum : userTypeEnums) {
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
