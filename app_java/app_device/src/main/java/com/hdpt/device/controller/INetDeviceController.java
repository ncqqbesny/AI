package com.hdpt.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.utils.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface INetDeviceController {
    //990202143	保存IP地址	保存IP地址	主柜《系统配置》
    public ServerResponse<?> saveListInfo(List<NetDeviceVo> netDeviceVos);
    //更新网络设备信息
    public ServerResponse<?> updateNetDeviceListInfo(List<NetDeviceVo> saveListInfo);
    //删除信息
    public ServerResponse<?> delInfo(List<Integer> ids);
    //导入网络设备excel文件
    public ServerResponse<?> importNetDevice(MultipartFile file) throws Exception;
    //灭火器识别程序
    public ServerResponse<?> upFilesFirexIdentify(MultipartFile[] files, HttpServletRequest requestNew,String serialNo);
}
