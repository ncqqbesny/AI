package com.app.device.job.jobhandler;


import com.app.device.services.IDeviceExtendService;
import com.app.device.services.IExtendDescService;
import com.app.device.utils.StringUtil;
import com.app.device.services.IDeviceTypeService;
import com.app.device.services.INetDeviceQueryService;
import com.app.device.utils.FileUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FirexExtendXxjob {
    private static Logger logger = LoggerFactory.getLogger(FirexExtendXxjob.class);
    @Autowired
    IDeviceExtendService deviceExtendService;
    @Autowired
    INetDeviceQueryService netDeviceQueryService;
    @Autowired
    IDeviceTypeService deviceTypeService;
    @Autowired
    IExtendDescService extendDescService;



    /**
     * 1、
     */
    @XxlJob("firexExtendJobHandler")
    public void firexExtendJobHandler() throws Exception {
        //1
        XxlJobHelper.log("灭火器AI识识数据开始....");
        //读取文本文件数据
        Map<String, String> fileParamMap = netDeviceQueryService.getFirexConfig();
        if (fileParamMap == null || StringUtil.isEmpty(fileParamMap.get("filePath")) || StringUtil.isEmpty(fileParamMap.get("out_filename"))) {
            XxlJobHelper.log("没有找到对应的灭火器配置的文件信息");
            return;
        }
        String out_filename=fileParamMap.get("out_filename");
        String filePath=fileParamMap.get("filePath");
        List<String> readFileData=FileUtils.readFileData(out_filename,filePath,"UTF-8");

        for(String aiInfoStr:readFileData){
            //写入对的数据
            String [] aiInfos=aiInfoStr.split(";");
            if(aiInfos.length<4){
                XxlJobHelper.log("读取的数据长度不正确===="+aiInfoStr);
                continue;
            }
            String fileName=aiInfos[0];
            String[] fileGids=fileName.split("_");
            if(fileGids.length<3 || !fileName.contains("jpg")){
                XxlJobHelper.log("读取的文件数据不正确===="+fileName);
                continue;
            }
            Map<String,String> paramMap=new HashMap<>();
            paramMap.put("exe_result",aiInfos[1]);
            paramMap.put("color",aiInfos[2]);
            paramMap.put("ratio",aiInfos[3]);
            paramMap.put("deviceGid",fileGids[fileGids.length-1].replace(".jpg",""));
            paramMap.put("file_name",fileName);
            paramMap.put("pic_url",fileParamMap.get("picFilePath"));
            String msg=deviceExtendService.insertUpdate(paramMap,"灭火器",0);
            if(StringUtil.isNotEmpty(msg)){
                XxlJobHelper.log("添加数据不正确===="+msg);
                continue;
            }
            if(!FileUtils.delTextByFile(fileParamMap.get("out_filename"),fileParamMap.get("filePath"),aiInfoStr,"UTF-8")){
                XxlJobHelper.log("清数据数据不成功===="+aiInfoStr);
                continue;
            }

        }
        //删除文本文件数据

        XxlJobHelper.log("火器AI识识数据结束....");
    }


}
