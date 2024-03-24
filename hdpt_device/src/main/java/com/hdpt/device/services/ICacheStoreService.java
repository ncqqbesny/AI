package com.hdpt.device.services;

public interface ICacheStoreService {
    //存
    void setCommonCache(String key,Object value);
    //取
    Object getCommonCache(String key);
    //删除
    void   delCommonCache(String key);

}
