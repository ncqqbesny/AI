package com.soft.mapp.basecenter.impl.services;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @date 2023-06-05
 * @Descripion: 计数服务
 */
@Service
public class CountService {

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 新增在线人数
     */
    public void incr(int cnt) {
        atomicInteger.addAndGet(cnt);
    }

    /**
     * 获取当前在线的人数
     */
    public int getOnlineCnt() {
        return atomicInteger.get();
    }
}