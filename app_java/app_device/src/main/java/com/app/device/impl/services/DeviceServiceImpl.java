package com.app.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.app.device.dao.IDeviceDao;
import com.app.device.dao.IDeviceTypeDao;
import com.app.device.domain.Device.*;
import com.app.device.domain.message.MessageRecordModel;
import com.app.device.services.IDeviceExtendService;
import com.app.device.services.IExtendDescService;
import com.app.device.services.IHardwareRevService;
import com.app.device.type.BaseConst;
import com.app.device.type.DeviceTypeEnum;
import com.app.device.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.app.device.dao.IDeviceExtendDao;
import com.app.device.domain.Device.*;
import com.app.device.handler.CommonBusiness;
import com.app.device.services.*;
import com.app.device.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DeviceServiceImpl implements IDeviceService {
    private final static Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Autowired
    IDeviceDao deviceDao;
    @Autowired
    IDeviceExtendDao deviceExtendDao;
    @Autowired
    IDeviceTypeService deviceTypeService;
    @Autowired
    IExtendDescService extendDescService;
    @Autowired
    IDeviceExtendService deviceExtendService;
    @Autowired
    INetDeviceQueryService netDeviceQueryService;
    @Autowired
    IHardwareRevService hardwareRevService;
    @Autowired
    IDeviceTypeDao deviceTypeDao;
    @Value("${rabbitmq.springboot.queue}")
    private String rabbitmqQueue;
    @Value("${rabbitmq.springboot.exchange}")
    private String rabbitmqExchange;
    @Value("${rabbitmq.springboot.key}")
    private String rabbitmqKey;
    @Value("${http.pre.url}")
    private String basePath;
    //@Resource(name = "appRabbitTempleate")
    private RabbitTemplate rabbitTemplate;
    /**
     * 上传地址
     */
    @Value("${file.upload.path}")
    private String path;

    @Override
    public List<Map> mySelect(String sql) {

        return null;
    }

    @Override
    public int selectColumn(String gid) {
        //int count=deviceExtendDao.selectColumn(gid);
        //System.out.println("count===="+count);
        return 0;
    }

    /**
     * 添加信息包括扩展信息
     *
     * @param dataMap
     * @param typeName
     * @param mId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public String insertUpdate(Map<String, String> dataMap, String typeName, Integer mId) throws InvocationTargetException, IllegalAccessException {
        List<DeviceTypeDTO> deviceTypeDTOList = deviceTypeService.findByName(typeName);
        if (CollectionUtil.isEmpty(deviceTypeDTOList)) {
            return "没有相应的类型====" + typeName + "的编号";
        }
        List<ExtendDescDTO> extendDescDTOList = extendDescService.findByTypeCode(deviceTypeDTOList.get(0).getTypeCode(), mId);
        if (CollectionUtil.isEmpty(extendDescDTOList)) {
            return "没有相应的扩展信息====" + deviceTypeDTOList.get(0).getTypeCode() + "的类型编号";
        }
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceTypeCode(deviceTypeDTOList.get(0).getTypeCode());
        deviceDTO.setCatalogCode(extendDescDTOList.get(0).getCatalogCode());
        deviceDTO.setDescCode(extendDescDTOList.get(0).getDescCode());
        deviceDTO.setSystemCode(dataMap.get("systemCode"));
        deviceDTO.setDeviceName(typeName);
        if (StringUtil.isNotEmpty(dataMap.get("serialNo"))) {
            deviceDTO.setDeviceSn(dataMap.get("serialNo"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("deviceSn"))) {
            deviceDTO.setDeviceSn(dataMap.get("deviceSn"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("gatewaySn"))) {
            deviceDTO.setGateway(dataMap.get("gatewaySn"));
        }
        deviceDTO.setMid(mId);
        if (StringUtil.isNotEmpty(dataMap.get("keepalive"))) {
            deviceDTO.setInterval(dataMap.get("keepalive"));
        }
        deviceDTO.setOpHappTm(DateUtils.getDateByString(dataMap.get("collectTime"), DateUtils.DATE_FORMAT));
        //deviceDTO.setCurrentTime(DateUtils.getDateByString(dataMap.get("collectTime"), DateUtils.DATE_FORMAT));
        if (StringUtil.isNotEmpty(dataMap.get("reportAt"))) {
            deviceDTO.setOpHappTm(DateUtils.convertToDateTime(dataMap.get("reportAt")));
        }
        //状态处理
        if (StringUtil.isNotEmpty(dataMap.get("stauts"))) {
            deviceDTO.setStatus(dataMap.get("stauts"));
        }

        deviceDTO.setLatitude(dataMap.get("latitude"));
        deviceDTO.setLongitude(dataMap.get("longitude"));
        if (StringUtil.isNotEmpty(dataMap.get("province"))) {
            deviceDTO.setProvince(dataMap.get("province"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("province"))) {
            deviceDTO.setProvince(dataMap.get("province"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("city"))) {
            deviceDTO.setCity(dataMap.get("city"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("area"))) {
            deviceDTO.setArea(dataMap.get("area"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("address"))) {
            deviceDTO.setAddress(dataMap.get("address"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("displayDeviceName"))) {
            deviceDTO.setDisplayDeviceName(dataMap.get("displayDeviceName"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("displaySortorder"))) {
            deviceDTO.setDisplaySortorder(dataMap.get("displaySortorder"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("sortorderDeviceName"))) {
            deviceDTO.setSortorderDeviceName(dataMap.get("sortorderDeviceName"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("deviceName"))) {
            deviceDTO.setDeviceName(dataMap.get("deviceName"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("deviceSw"))) {
            deviceDTO.setDeviceSw(dataMap.get("deviceSw"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("deviceHw"))) {
            deviceDTO.setDeviceHw(dataMap.get("deviceHw"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("mac"))) {
            deviceDTO.setMac(dataMap.get("mac"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("gid"))) {
            deviceDTO.setGid(dataMap.get("gid"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("deviceTypeCode"))) {
            deviceDTO.setDeviceTypeCode(dataMap.get("deviceTypeCode"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("mid"))) {
            deviceDTO.setMid(ConvertUtils.toInt(dataMap.get("mid")));
        } else {
            deviceDTO.setMid(mId);
        }
        if (StringUtil.isNotEmpty(dataMap.get("outTime"))) {
            deviceDTO.setOutTime(DateUtils.getDateByString(dataMap.get("outTime"), DateUtils.DATE_FORMAT));
        }
        if (StringUtil.isNotEmpty(dataMap.get("ip"))) {
            deviceDTO.setIp(dataMap.get("ip"));
        }
        if (StringUtil.isNotEmpty(dataMap.get("bindTime"))) {
            deviceDTO.setBindTime(DateUtils.getDateByString(dataMap.get("bindTime"), DateUtils.DATE_FORMAT));
        }
        deviceDTO.setUpJson(dataMap.get("upJson"));
        int count = 0;
        QueryWrapper<DeviceDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(deviceDTOQueryWrapper -> deviceDTOQueryWrapper.eq("GID", deviceDTO.getGid()))
                .or(deviceDTOQueryWrapper1 -> deviceDTOQueryWrapper1.eq("DEVICETYPECODE", deviceTypeDTOList.get(0).getTypeCode()).eq("DEVICESN", deviceDTO.getDeviceSn()));
        List<DeviceDTO> list = deviceDao.selectList(queryWrapper);
        //在线刷新间隔时间
        if (StringUtil.isEmpty(deviceDTO.getInterval()) && StringUtil.isNotEmpty(deviceDTO.getDeviceSn()) && CollectionUtil.isNotEmpty(list)) {
            deviceDTO.setInterval(getIntervalByDeviceSn(deviceDTO.getDeviceSn(), list.get(0).getMid()));
        }
        if (StringUtil.isEmpty(deviceDTO.getInterval()) && StringUtil.isNotEmpty(deviceDTO.getDeviceSn()) && CollectionUtil.isEmpty(list)) {
            deviceDTO.setInterval(getIntervalByDeviceSn(deviceDTO.getDeviceSn(), deviceDTO.getMid()));
        }
        if (CollectionUtil.isNotEmpty(list)) {
            Long between = DateUtils.getLongDays(new Date(), list.get(0).getLastTime());
            long s = between / 1000;
            long interval = ConvertUtils.toLong(list.get(0).getInterval()) + 30;
            //放置运行时间的起始时间
            if ("0".equals(list.get(0).getStatus()) && s > interval && typeName.equals(DeviceTypeEnum.sfx.toString())) {
                deviceDTO.setCurrentTime(new Date());
            }
            if ("0".equals(list.get(0).getStatus()) && s / 60 > interval && typeName.equals(DeviceTypeEnum.mhq.toString())) {
                deviceDTO.setCurrentTime(new Date());
            }
            //已存在设备名称不在修改。
            if (StringUtil.isNotEmpty(list.get(0).getDeviceName())) {
                deviceDTO.setDeviceName(list.get(0).getDeviceName());
            }
            //如果为上自动上传信息默认mid=0
            if (0 == deviceDTO.getMid()) {
                deviceDTO.setMid(null);
            }
            if (list.get(0).getMid() > 0) {
                String sql = "update merchant set device='" + typeName + "' where m_id=" + list.get(0).getMid();
                boolean updateFlag = SqlRunner.db().update(sql);
                String sql1 = "select m_name from merchant" + " where m_id=" + list.get(0).getMid();
                Map<String, Object> map = SqlRunner.db().selectOne(sql1);
                if (map != null && map.get("m_name") != null) {
                    deviceDTO.setMName(map.get("m_name").toString());
                }
                //解决上传时因为有mid更新问题。
                deviceDTO.setMid(list.get(0).getMid());
            }
            count = deviceDao.update(deviceDTO, queryWrapper);
            deviceDTO.setGid(list.get(0).getGid());

        } else {
            count = deviceDao.insert(deviceDTO);
        }
        if (count == 0) {
            return "添加信息失败";
        }
        String msg = "";
        if (typeName.contains(DeviceTypeEnum.mhq.toString())) {
            String imgPath = saveMhqPic(dataMap, deviceDTO.getGid());
            dataMap.put("img", imgPath);
        }
        if (StringUtil.isNotEmpty(deviceDTO.getGid())) {
            dataMap.put("deviceGid", deviceDTO.getGid());
            msg = deviceExtendService.insertUpdate(dataMap, typeName, deviceDTO.getMid());
        }
        if (StringUtil.isNotEmpty(msg)) {
            return msg;
        }
        return "";
    }

    /**
     * 保存6ase64 灭火器图片
     *
     * @param dataMap
     * @param gid
     * @return
     */
    private String saveMhqPic(Map<String, String> dataMap, String gid) {
        if (StringUtil.isEmpty(dataMap.get("img"))) {
            log.info("saveMhqPic-灭火器没有图片");
            return "灭火器没有base64图片";
        }
        //保存成功后，存储图片
        Map<String, String> fileParamMap = netDeviceQueryService.getFirexConfig();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String prefixFile = sf.format(new Date());
        //生成文件名称已uuid命名
        String uuid = gid;
        String fileNewName = prefixFile + "_" + dataMap.get("serialNo") + "_" + uuid + ".jpg";
        String jpgBase64 = dataMap.get("img");
        //String basePath = fileParamMap.get("host_url");
        String showUrl = basePath + path + "/" + BaseConst.File_IMG_BAK+fileNewName;
        fileParamMap.put("serialNo", dataMap.get("serialNo"));
        fileParamMap.put("jpgBase64", jpgBase64);
        fileParamMap.put("showUrl", showUrl);
        fileParamMap.put("fileName", fileNewName);
        Map<String, Object> map = netDeviceQueryService.savePicAndTextFile(fileParamMap);
        if (!map.get("code").toString().equals("200")) {
            log.info("saveMhqPic error:" + map.get("msg").toString());
            return map.get("msg").toString();
        }
        return fileParamMap.get("showUrl");
    }

    /**
     * 按设备编号获得上传时间间隔
     *
     * @param DeviceSn
     * @return
     */
    private String getIntervalByDeviceSn(String DeviceSn, Integer mid) {
        Date upTime = new Date();
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setDeviceSn(DeviceSn);
        deviceVo.setMid(mid);
        List<?> deviceList = getSmartDeviceListBySearch(deviceVo);
        if (CollectionUtil.isNotEmpty(deviceList)) {
            DeviceDTO deviceDTO = (DeviceDTO) deviceList.get(0);
            if (null != deviceDTO.getOpHappTm()) {
                upTime = deviceDTO.getOpHappTm();
            }
        }
        Long between = DateUtils.getLongDays(new Date(), upTime);
        long s = Math.abs(between / 1000);
        return ConvertUtils.toString(s);
    }


    @Override
    public List<?> findByDeviceGid(String deviceGid, String mid) {

        return null;
    }

    @Override
    public CommonPage<?> getPageList(Integer pageSize, Integer pageNum, Integer sort, DeviceVo deviceVo) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        //分页参数
        Page<DeviceDTO> page = new Page<>(pageNum, pageSize);
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<DeviceDTO> queryWrapper = getQueryWrapper(sort, deviceVo);
        deviceDao.selectPage(page, queryWrapper);
        //page.getRecords().forEach(System.out::println);
        Long total = page.getTotal();
        List<DeviceDTO> list = page.getRecords();
        List<Object> deviceVoList = getDeviceAndExtendList(list);
        CommonPage<?> result = CommonPage.restPage(deviceVoList, pageNum, pageSize, total);
        return result;
    }

    /**
     * 获得变动字段组装LIST信息
     *
     * @param list
     * @return
     * @throws IllegalAccessException
     */
    private List<Object> getDeviceAndExtendList(List<DeviceDTO> list) throws IllegalAccessException {
        List<Object> deviceVoList = new ArrayList<>();
        for (DeviceDTO deviceDTO : list) {
            DeviceVo deviceVo1 = new DeviceVo();
            BeanUtils.copyProperties(deviceDTO, deviceVo1);
            Map<String, Object> map = deviceExtendService.findByDeviceGidAndMid(deviceDTO.getDeviceTypeCode(), deviceDTO.getGid(), deviceDTO.getMid());
            //不返回上传json字段
            deviceVo1.setUpJson("");
            Object obj = DynamicBeanUtils.getTarget(deviceVo1, map);
            deviceVoList.add(obj);
        }
        return deviceVoList;
    }

    @Override
    public List<?> getSmartDeviceListByGids(List<String> gids) throws IllegalAccessException {
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setGids(gids);
        return getSmartDeviceListBySearch(deviceVo);
    }

    @Override
    public List<?> getSmartDeviceListByMids(List<String> mIds, List<String> deviceTypeCodes) throws IllegalAccessException {
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setMids(mIds);
        deviceVo.setDeviceTypeCodes(deviceTypeCodes);
        return getSmartDeviceListBySearch(deviceVo);
    }

    /**
     * 按需求查询智能设备信息
     *
     * @param deviceVo
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public List<?> getSmartDeviceListBySearch(DeviceVo deviceVo) {
        LambdaQueryWrapper<DeviceDTO> queryWrapper = getQueryWrapper(1, deviceVo);
        try {
            List<DeviceDTO> list = deviceDao.selectList(queryWrapper);
            List<Object> deviceVoList = getDeviceAndExtendList(list);
            return deviceVoList;
        } catch (Exception e) {
            log.info("getSmartDeviceListBySearch==查询错误：" + e);
        }
        return null;
    }

    /**
     * 获得得灭火器token
     *
     * @return
     */
    @Override
    public Map<String, String> getFireAccessToken() {
        Map<String, String> firexConfigMap = netDeviceQueryService.getFirexConfig();
        String paramJson = "{\"appid\":" + "\"" + firexConfigMap.get("appid") + "\",\"accesskey\":\"" + firexConfigMap.get("accesskey") + "\"}";
        String url = firexConfigMap.get("request_url") + firexConfigMap.get("token_url");
        String resultStr = HttpTools.doJsonPost(url, paramJson, null);
        if (StringUtil.isEmpty(resultStr)) {
            return null;
        }
        JSONObject returnJson = JSONObject.parseObject(resultStr, Feature.OrderedField);
        log.info("getFireAccessToken return JSON-->" + resultStr);
        if (null == returnJson.getString("statusCode") || !returnJson.getString("statusCode").equals("200")) {
            return null;
        }
        firexConfigMap.put("token", returnJson.getString("access_token"));
        return firexConfigMap;
    }

    /**
     * 获得所有设备信息
     *
     * @return
     */
    @Override
    public List<String> getAllFireDeviceList() {
        Map<String, String> firexConfigMap = getFireAccessToken();
        if (firexConfigMap == null) {
            return null;
        }
        String url = firexConfigMap.get("request_url") + firexConfigMap.get("device_list_url");
        String token = firexConfigMap.get("token");
        String resultStr = HttpTools.doGet(url, token);
        if (StringUtil.isEmpty(resultStr)) {
            return null;
        }
        JSONObject returnJson = JSONObject.parseObject(resultStr, Feature.OrderedField);
        log.info("getAllFireDeviceList return JSON-->" + resultStr);
        if (null == returnJson.getString("statusCode") || !returnJson.getString("statusCode").equals("200")) {
            return null;
        }
        JSONArray deviceListValues = returnJson.getJSONArray("devices");
        List<String> devices = new ArrayList<>();
        for (int i = 0; i < deviceListValues.size(); i++) {
            devices.add(deviceListValues.getString(i));
        }
        return devices;
    }

    /**
     * 远程配置灭火器设备
     *
     * @param remoteCtrlVo
     * @return
     */
    @Override
    public String remoteCtrlFireDevice(RemoteCtrlVo remoteCtrlVo) {
        List<String> devices = new ArrayList<>();
        if (StringUtil.isEmpty(remoteCtrlVo.getSerialNo())) {
            devices = getAllFireDeviceList();
        } else {
            devices.add(remoteCtrlVo.getSerialNo());
        }
        Map<String, String> firexConfigMap = getFireAccessToken();
        if (firexConfigMap == null) {
            return "remoteCtrlFireDevice方法,没有获得token";
        }
        String url = firexConfigMap.get("request_url") + firexConfigMap.get("remote_url");
        String token = firexConfigMap.get("token");
        for (String deviceStr : devices) {
            remoteCtrlVo.setSerialNo(deviceStr);
            String resultStr = HttpTools.doJsonPost(url, JSON.toJSONString(remoteCtrlVo), token);
            if (StringUtil.isEmpty(resultStr)) {
                return "请求远程控制网络失败！";
            }
            JSONObject returnJson = JSONObject.parseObject(resultStr, Feature.OrderedField);
            log.info("remoteCtrlFireDevice return JSON-->" + resultStr);
            if (null == returnJson.getString("statusCode") || !returnJson.getString("statusCode").equals("200")) {
                return "请求远程控制失败！";
            }
        }
        return "";
    }

    /**
     * 订阅
     *
     * @param serialNos
     * @return
     */
    @Override
    public String subscribeFireDevice(String subscribe, List<String> serialNos) {
        List<String> devices = new ArrayList<>();
        if (CollectionUtil.isEmpty(serialNos)) {
            devices = getAllFireDeviceList();
        } else {
            devices = serialNos;
        }
        Map<String, String> firexConfigMap = getFireAccessToken();
        if (firexConfigMap == null) {
            return "subscribeFireDevice方法,没有获得token";
        }
        String url = firexConfigMap.get("request_url") + firexConfigMap.get("subscribe_url");
        String token = firexConfigMap.get("token");
        SubscribeVo subscribeVo = new SubscribeVo();
        subscribeVo.setSubscribe("ON");
        if (StringUtil.isNotEmpty(subscribe)) {
            subscribeVo.setSubscribe(subscribe);
        }
        subscribeVo.setDevices(devices);
        subscribeVo.setCallback_url(firexConfigMap.get("host_url") + firexConfigMap.get("callback_url"));
        String resultStr = HttpTools.doJsonPost(url, JSON.toJSONString(subscribeVo), token);
        if (StringUtil.isEmpty(resultStr)) {
            return "请求远程控制网络失败！";
        }
        JSONObject returnJson = JSONObject.parseObject(resultStr, Feature.OrderedField);
        log.info("remoteCtrlFireDevice return JSON-->" + resultStr);
        if (null == returnJson.getString("statusCode") || !returnJson.getString("statusCode").equals("200")) {
            return "请求远程控制失败！";
        }
        return "";
    }

    @Override
    public String sendMessage() {
        String uniqueId = System.currentTimeMillis() + "#" + UUID.randomUUID();
        List<String> test = new ArrayList<>();
        test.add("yzg1");
        test.add("yzg2");
        String messageContent = JSONObject.toJSONString(test);
        MessageRecordModel messageRecordModel = new MessageRecordModel();
        messageRecordModel.setExchange(rabbitmqExchange);
        messageRecordModel.setRoutionKey(rabbitmqKey);
        messageRecordModel.setMessageContent(messageContent);

        //这是向rabbit投递消息的和逻辑==================================================
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(uniqueId);
        MessageProperties messageProperties = new MessageProperties();
        //设置消息的持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setMessageId(uniqueId);

        Message message = new Message(messageContent.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(messageRecordModel.getExchange(), messageRecordModel.getRoutionKey(), message, correlationData);
        return "";
    }

    @Override
    public String updateByExampleSelective(List<DeviceVo> saveListInfo) {
        return null;
    }

    @Override
    public String delInfo(List<String> gids) {
        for (String gid : gids) {
            QueryWrapper<DeviceDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("GID", gid);
            QueryWrapper<DeviceExtendDTO> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("DEVICEGID", gid);
            int count = deviceDao.delete(queryWrapper);
            count = deviceExtendDao.delete(queryWrapper1);
            if (count == 0) {
                log.info("delInfo--->删除智能设备信息gid:" + gid + "没有删除成功");
            }

        }
        return null;
    }

    /**
     * 保存智能设备
     *
     * @param data
     * @return
     */
    @Override
    public String saveDeviceInfo(String data) {
        String msg = "";
        if (!StringUtil.isJSON(data)) {
            return "参数不是json对象";
        }
        JSONObject returnJson = JSONObject.parseObject(data, Feature.OrderedField);
        try {
            String deviceTypeCode = returnJson.getString("deviceTypeCode");
            if(StringUtil.isEmpty(deviceTypeCode)){
                return "deviceTypeCode参数没有填写或为空";
            }
            QueryWrapper<DeviceTypeDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("typeCode", deviceTypeCode);
            List<DeviceTypeDTO> list = deviceTypeDao.selectList(queryWrapper);
            String deviceTypeName = "";
            if (CollectionUtil.isNotEmpty(list)) {
                deviceTypeName = list.get(0).getDeviceTypeName();
            }
            if (deviceTypeName.equals(DeviceTypeEnum.mhq.toString())) {
                msg = hardwareRevService.smartDevUpInfo(data, returnJson.getString("stauts"));
            }
            if (deviceTypeName.equals(DeviceTypeEnum.sfx.toString())) {
                msg = hardwareRevService.smartSfxDevUpInfo(data, returnJson.getString("stauts"));
            }
            if(deviceTypeName.contains(DeviceTypeEnum.yjc.toString())){
                msg=hardwareRevService.smartYjcDevUpJson(data,returnJson.getString("stauts"));
            }
        } catch (Exception e) {
            return "程序错误";
        }
        return msg;
    }

    /**
     * 存在相同设备编号
     *
     * @param deviceVo
     * @return
     */
    private boolean isExistDeviceSn(DeviceVo deviceVo) {
        Integer sort = 0;
        List<?> list = getSmartDeviceListBySearch(deviceVo);
        if (CollectionUtil.isNotEmpty(list)) {
            return true;
        }
        return false;
    }

    /**
     * 查询条件组装
     *
     * @param sort
     * @param deviceVo
     * @return
     */
    private LambdaQueryWrapper<DeviceDTO> getQueryWrapper(Integer sort, DeviceVo deviceVo) {
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<DeviceDTO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtil.isNotEmpty(deviceVo.getDeviceTypeCode())) {
            queryWrapper.eq(DeviceDTO::getDeviceTypeCode, deviceVo.getDeviceTypeCode());
        }
        //gid集合
        if (CollectionUtil.isNotEmpty(deviceVo.getGids())) {
            queryWrapper.in(DeviceDTO::getGid, deviceVo.getGids());
        }
        //mid集合
        if (CollectionUtil.isNotEmpty(deviceVo.getMids())) {
            queryWrapper.in(DeviceDTO::getMid, deviceVo.getMids());
        }
        //typeCode集合
        if (CollectionUtil.isNotEmpty(deviceVo.getDeviceTypeCodes())) {
            queryWrapper.in(DeviceDTO::getDeviceTypeCode, deviceVo.getDeviceTypeCodes());
        }
        if (StringUtil.isNotEmpty(deviceVo.getMid()) && !CommonBusiness.isAdmin() && !deviceVo.isJob()) {
            queryWrapper.eq(DeviceDTO::getMid, deviceVo.getMid());
        }
        if(StringUtil.isNotEmpty(deviceVo.getStatus())){
            queryWrapper.eq(DeviceDTO::getStatus, deviceVo.getStatus());
        }
        if (StringUtil.isNotEmpty(deviceVo.getDeviceSn())) {
            queryWrapper.eq(DeviceDTO::getDeviceSn, deviceVo.getDeviceSn());
        }
        if (StringUtil.isNotEmpty(deviceVo.getDeviceName())) {
            queryWrapper.like(DeviceDTO::getDeviceName, deviceVo.getDeviceName());
        }
        if (StringUtil.isNotEmpty(deviceVo.getDisplayDeviceName())) {
            queryWrapper.like(DeviceDTO::getDisplayDeviceName, deviceVo.getDisplayDeviceName());
        }
        if (StringUtil.isNotEmpty(deviceVo.getUserName())) {
            queryWrapper.like(DeviceDTO::getUserName, deviceVo.getUserName());
        }
        if (StringUtil.isNotEmpty(deviceVo.getMName())) {
            queryWrapper.like(DeviceDTO::getMName, deviceVo.getMName());
        }
        //排序 0，降序，1，升序
        if (sort == 1) {
            queryWrapper.orderByAsc(DeviceDTO::getCreateTime);
        } else {
            queryWrapper.orderByDesc(DeviceDTO::getCreateTime);
        }
        return queryWrapper;
    }
}
