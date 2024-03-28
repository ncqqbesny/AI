package com.soft.mapp.basecenter.type;

/**
 * 同步类型（1同步用户基础信息，2断点同步用户详细信息，3全量同步用户详细信息）
 */
public enum SyncTypeEnum {
    basic("同步用户基础信息","1"),
    breakpoint("断点同步用户详细信息","2"),
    all("全量同步用户详细信息","3"),
    ;
    // 成员变量
    private String name;
    private String index;
    // 构造方法
    private SyncTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }
    public String getNameById(String index){
        if(index == null){
            return null;
        }
        for(SyncTypeEnum v : values()) {
            if(v.index == index) {
                return v.name;
            }
        }
        return null;
    }
    public static String getValue(String name) {
        SyncTypeEnum[]  userTypeEnums = values();
        for (SyncTypeEnum userTypeEnum : userTypeEnums) {
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
