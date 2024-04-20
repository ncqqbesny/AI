package com.app.device.impl.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.app.device.services.ICacheStoreService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CacheStoreServiceImpl implements ICacheStoreService {
    private Cache<String,Object> commonStoreCache=null;

    @PostConstruct//代理此bean时会首先执行该初始化方法
    public void init(){
        commonStoreCache= CacheBuilder.newBuilder()
                //设置缓存容器的初始化容量为10（可以存10个键值对）
                .initialCapacity(10)
                //最大缓存容量是100,超过100后会安装LRU策略-最近最少使用，具体百度-移除缓存项
                .maximumSize(5000)
                .build();
    }
    @Override
    public void setCommonCache(String key, Object value) {
        commonStoreCache.put(key,value);
    }
    @Override
    public Object getCommonCache(String key) {
        return commonStoreCache.getIfPresent(key);
    }

    @Override
    public void delCommonCache(String key) {
        commonStoreCache.invalidate(key);
    }
}
