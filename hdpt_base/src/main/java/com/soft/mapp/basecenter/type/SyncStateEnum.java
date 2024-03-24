package com.soft.mapp.basecenter.type;

/**
 * 同步状态（1等待执行，2同步中，3同步完成，4同步终止）
 */
public enum SyncStateEnum {
    wait("等待执行","1"),
    syncing("同步中","2"),
    finish("同步完成","3"),
    stop("同步终止","4"),
    ;
    // 成员变量
    private String name;
    private String index;
    // 构造方法
    private SyncStateEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }
    public String getNameById(String index){
        if(index == null){
            return null;
        }
        for(SyncStateEnum v : values()) {
            if(v.index == index) {
                return v.name;
            }
        }
        return null;
    }
    public static String getValue(String name) {
        SyncStateEnum[]  userTypeEnums = values();
        for (SyncStateEnum userTypeEnum : userTypeEnums) {
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
