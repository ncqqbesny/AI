package com.app.device.controller;

import com.app.device.utils.ServerResponse;

import java.io.IOException;

public interface IHardQueryScreenController {
    /**
     * 设备分布数据
     * @return
     */
    public ServerResponse<?> getProvinceDeviceNum(Integer mId) throws IOException;
    /**
     * 设备地图数据
     * @return
     */
    public ServerResponse<?> getAreaDeviceNum(Integer mId) throws IOException;

    /**
     * 根据在线和离线状态数量     *
     * @return
     */
    public ServerResponse<?> getStatusDeviceNum(Integer mId);

    /**
     * 获得设备类型名称数量
     * @return
     */
    public ServerResponse<?> getDeviceTypeNum(Integer mId);

    /**
     * 设备最新的更新的状态和位置,参数控制显示记录数
     * @return
     */

    public ServerResponse<?> getDeviceAreaStatus(Integer displayLine,Integer mId);


}
