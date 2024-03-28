package com.hdpt.device.impl.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hdpt.device.controller.IHardwareRevController;
import com.hdpt.device.controller.INetDeviceController;
import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceVo;
import com.hdpt.device.domain.system.AutoLog;
import com.hdpt.device.services.INetDeviceQueryService;
import com.hdpt.device.services.INetDeviceService;
import com.hdpt.device.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

//@Slf4j
@RestController
@Api(tags = "网络设备操作接口")//描述UserController的信息
@RequestMapping("/hdptdevice")
public class NetDeviceController implements INetDeviceController {
    private final static Logger log = LoggerFactory.getLogger(NetDeviceController.class);
    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String path;
    @Autowired
    INetDeviceService netDeviceService;
    @Autowired
    INetDeviceQueryService netDeviceQueryService;
    @Override
    @ApiOperation(value = "保存网络设备信息", notes = "批量添加和编辑网络设备信息")
    @RequestMapping(value = "/saveNetDeviceListInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 8) //排序
    @AutoLog(value ="保存网络设备信息", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> saveListInfo(@RequestBody List<NetDeviceVo> saveListInfo) {
        String msg = netDeviceService.saveListInfo(saveListInfo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
    @Override
    @ApiOperation(value = "更新网络设备信息", notes = "批量编辑网络设备信息")
    @RequestMapping(value = "/updateNetDeviceListInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 8) //排序
    @AutoLog(value ="更新网络设备信息", operateType = 3, logType = 1,paramType = 0)
    public ServerResponse<?> updateNetDeviceListInfo(@RequestBody List<NetDeviceVo> saveListInfo) {
        String msg = netDeviceService.updateByExampleSelective(saveListInfo);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
    @Override
    @ApiOperation(value = "删除网络设备信息", notes = "按id批量删除网络设备信息")
    @RequestMapping(value = "/delLogInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    @AutoLog(value ="删除网络设备信息", operateType = 4, logType = 1,paramType = 3)
    public ServerResponse<?> delInfo(@RequestBody List<Integer> ids) {
        String msg = netDeviceService.delInfo(ids);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
    @PostMapping("/import")
    @Override
    @ApiOperation(value = "导入网络设备信息", notes = "导入excel信息")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperationSupport(order = 9) //排序
    public ServerResponse<?> importNetDevice(@RequestPart("file") MultipartFile file) throws Exception {
        List<NetDeviceVo> netDeviceDTOList = ExcelUtils.readMultipartFile(file, NetDeviceVo.class);
        String remark= DateUtils.date2Str(DateUtils.DATE_FORMAT,new Date())+"导入"+file.getOriginalFilename().toLowerCase()+" 文件生成";
        String msg = netDeviceService.saveExcelInfo(netDeviceDTOList,remark);
        if (StringUtil.isNotEmpty(msg)) {
            return ServerResponse.createByErrorMessage(msg);
        }
        return ServerResponse.createBySuccess();
    }
    @ResponseBody
    @PostMapping("/upFilesFirexIdentify")
    @ApiOperation(value = "上传文件进行灭火器识别", notes = "上传灭火器读取的图片，进行AI识别")
    public ServerResponse<?> upFilesFirexIdentify(@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest requestNew,String serialNo) {
        log.info("上传文件进行灭火器识别开始==============");
        if (files == null || files.length == 0) {
            return ServerResponse.createByErrorMessage("上传失败,请先选择文件");
        }
        long start = System.currentTimeMillis();
        List<Map<String,Object>> resultMapList=new ArrayList<>();
        try {
            Map<String,String> fileParamMap= netDeviceQueryService.getFirexConfig();
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            String prefixFile = sf.format(new Date());
            File fileNew = new File(fileParamMap.get("filePath"));
            if (!fileNew.exists()) {//判断目录是否存在
                fileNew.mkdirs();
            }
            for (MultipartFile singleFile : files) {
                log.info("上传单个文件开始;文件名:" + singleFile.getOriginalFilename());
                long localStartTime = System.currentTimeMillis();
                //保存图片文件名
                String fileNameExt = singleFile.getOriginalFilename();
                if (!fileNameExt.matches("^.*(jpg|JPG|png|gif|GIF|PNG|webp|WEBP|jpeg|JPEG)$")) {
                    return ServerResponse.createByErrorMessage("上传失败,不是jpg|png|gif|PNG|webp|jpeg图片");
                }
                //生成文件名称已uuid命名
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String[] fileNameArr = fileNameExt.split("\\.");
                String fileNewName = prefixFile+"_"+serialNo+"_"+uuid + "." + fileNameArr[fileNameArr.length - 1];
                //保存图片路径及新文件名
                String filePathNew = fileParamMap.get("picFilePath") + "/" + fileNewName;
                File newFile = new File(filePathNew);
                //保存好文件名
                singleFile.transferTo(newFile);
                //转为base64位
                String jpgBase64=PicUtils.getBase64ByJpg(filePathNew);
                String basePath = requestNew.getScheme() + "://" + requestNew.getServerName() + ":"
                        + requestNew.getServerPort() + requestNew.getContextPath();
                log.info("上传单个文件结束,【" + fileNameExt + "】所耗时间:" + (System.currentTimeMillis() - localStartTime) + "ms");
                String showUrl = basePath + path + "/" + fileNewName;
                fileParamMap.put("serialNo",serialNo);
                fileParamMap.put("jpgBase64",jpgBase64);
                fileParamMap.put("showUrl",showUrl);
                fileParamMap.put("fileName",fileNewName);
                Map<String,Object> map=netDeviceQueryService.savePicAndTextFile(fileParamMap);
                resultMapList.add(map);
            }
            log.info("批量上传文件结束==============总耗时:" + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            log.error("批量上传到服务器出错！", e);
            return ServerResponse.createByErrorMessage("批量上传到服务器出错！=======错误信息"+ e);
        }
        return ServerResponse.createBySuccess(resultMapList);
    }
}
