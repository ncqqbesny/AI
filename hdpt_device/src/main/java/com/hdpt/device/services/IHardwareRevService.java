package com.hdpt.device.services;


import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.InvocationTargetException;

public interface IHardwareRevService {


    String netDevUpInfo(String data);
    String qjgDevUpInfo(String data);
    //灭火器平台上传硬件信息
    String smartDevUpInfo(String data,String stauts) throws InvocationTargetException, IllegalAccessException;
    //守护星上传数据
    String smartSfxDevUpInfo(String data,String stauts);
}
