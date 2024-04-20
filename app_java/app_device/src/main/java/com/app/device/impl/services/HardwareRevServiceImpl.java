package com.app.device.impl.services;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.app.device.dao.ICabinetDao;
import com.app.device.dao.INetDeviceDao;
import com.app.device.domain.Cabinet.CabinetDeviceDTO;
import com.app.device.type.DeviceTypeEnum;
import com.app.device.domain.Cabinet.CabinetDTO;
import com.app.device.domain.hdUp.NetDeviceDTO;
import com.app.device.domain.hdUp.NetDeviceQuery;
import com.app.device.services.IDeviceExtendService;
import com.app.device.services.IDeviceService;
import com.app.device.services.IHardwareRevService;
import com.app.device.utils.ConvertUtils;
import com.app.device.utils.DateUtils;
import com.app.device.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class HardwareRevServiceImpl implements IHardwareRevService {
    @Autowired
    INetDeviceDao netDeviceDao;
    @Autowired
    ICabinetDao cabinetDao;
    @Autowired
    IDeviceService deviceService;
    @Autowired
    IDeviceExtendService deviceExtendService;
    private final static Logger log = LoggerFactory.getLogger(HardwareRevServiceImpl.class);

    @Override
    public String netDevUpInfo(String data) {
        NetDeviceDTO netDeviceDTO = new NetDeviceDTO();
        if (!StringUtil.isJSON(data)) {
            return "参数不是json对象";
        }
        JSONObject returnJson = JSONObject.parseObject(data, Feature.OrderedField);
        netDeviceDTO.setUpJson(data);
        JSONObject acSystemInfo = returnJson.getJSONObject("ac_system_info");
        netDeviceDTO.setStatus("1");
        if (null != acSystemInfo) {
            netDeviceDTO.setAcMac(acSystemInfo.getString("ac_mac"));
            netDeviceDTO.setDeviceType(ConvertUtils.toString(DeviceTypeEnum.wifi.ordinal()));
            netDeviceDTO.setAcDeviceSn(acSystemInfo.getString("ac_device_sn"));
            netDeviceDTO.setAcDeviceHw(acSystemInfo.getString("ac_device_hw"));
            netDeviceDTO.setAcDeviceSw(acSystemInfo.getString("ac_device_sw"));
        }
        JSONObject acIpConf = returnJson.getJSONObject("ac_ip_conf");
        if (null != acIpConf) {
            netDeviceDTO.setAcIp(acIpConf.getString("ac_ip"));
            netDeviceDTO.setUrl("http://" + acIpConf.getString("ac_ip"));
            netDeviceDTO.setAcDialMode(acIpConf.getString("ac_dial_mode"));
            netDeviceDTO.setAcSubmask(acIpConf.getString("ac_submask"));
            netDeviceDTO.setAcGateway(acIpConf.getString("ac_gateway"));
            JSONArray acDns = acIpConf.getJSONArray("ac_dns");
            if (null != acDns) {
                for (int z = 0; z < acDns.size(); z++) {
                    if (z == 0) {
                        netDeviceDTO.setAcDns(acDns.getString(z));
                    }
                }
            }
        }
        JSONObject acOtherInfo = returnJson.getJSONObject("ac_other_info");
        if (null != acOtherInfo) {
            netDeviceDTO.setAcCurrentTime(acOtherInfo.getString("ac_current_time"));
            netDeviceDTO.setAcRuntime(acOtherInfo.getString("ac_runtime"));
            netDeviceDTO.setAcTimeZone(acOtherInfo.getString("ac_time_zone"));
        }
        JSONObject acApOnlineInfo = returnJson.getJSONObject("ac_ap_online_info");
        if (null != acApOnlineInfo) {
            netDeviceDTO.setAcApOnlineNum(acApOnlineInfo.getString("ac_ap_online_num"));
            netDeviceDTO.setAcUserOnlineNum(acApOnlineInfo.getString("ac_user_online_num"));
            netDeviceDTO.setAcOnlineApInfo(acApOnlineInfo.getString("ac_online_ap_info"));
            Map<String, Integer> acIntegerMap = getIntervalByMac(acSystemInfo.getString("ac_mac"), acSystemInfo.getString("ac_device_sn"));
            netDeviceDTO.setInterval(acIntegerMap.get("interval"));
            if (acIntegerMap.get("id") != 0) {
                NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
                netDeviceQuery.createCriteria().andIdEqualTo(acIntegerMap.get("id"));
                int count = netDeviceDao.updateByExampleSelective(netDeviceDTO, netDeviceQuery);
                if (count == 0) {
                    return "没有update更新网络AC设备数据";
                }
            } else {
                int count = netDeviceDao.insertOrUpdate(netDeviceDTO);
                if (count == 0) {
                    return "没有insert更新网络AC设备数据";
                }
            }
            JSONArray acOnlineApInfo = acApOnlineInfo.getJSONArray("ac_online_ap_info");
            for (Object apObj : acOnlineApInfo) {
                JSONObject jsonApObj = (JSONObject) apObj;
                //ap信息添加
                JSONObject jsonApSystemObj = jsonApObj.getJSONObject("ap_system_info");
                NetDeviceDTO apNetDeviceDTO = new NetDeviceDTO();
                apNetDeviceDTO.setUpJson(acApOnlineInfo.getString("ac_online_ap_info"));
                Map<String, Integer> apIntegerMap = new HashMap<>();
                if (null != jsonApSystemObj) {
                    apNetDeviceDTO.setParentId(netDeviceDTO.getId());
                    apNetDeviceDTO.setModel(jsonApSystemObj.getString("ap_device_model"));
                    apNetDeviceDTO.setAcDeviceName(jsonApSystemObj.getString("ap_name"));
                    apNetDeviceDTO.setAcDeviceSn(jsonApSystemObj.getString("ap_device_sn"));
                    apNetDeviceDTO.setAcDeviceHw(jsonApSystemObj.getString("ap_device_hw"));
                    apNetDeviceDTO.setAcDeviceSw(jsonApSystemObj.getString("ap_device_sw"));
                    apNetDeviceDTO.setDeviceType(ConvertUtils.toString(DeviceTypeEnum.ap.ordinal()));
                    apNetDeviceDTO.setAcDialMode(jsonApSystemObj.getString("ap_wifi_mode"));
                    String mac = jsonApSystemObj.getString("ap_mac").replace(":", "-").toUpperCase();
                    apNetDeviceDTO.setAcMac(mac);
                    apIntegerMap = getIntervalByMac(mac, jsonApSystemObj.getString("ap_device_sn"));
                    apNetDeviceDTO.setInterval(apIntegerMap.get("interval"));
                    if (acIntegerMap.get("id") != 0) {
                        apNetDeviceDTO.setParentId(acIntegerMap.get("id"));
                    }
                    if (acIntegerMap.get("mid") != 0) {
                        apNetDeviceDTO.setMId(acIntegerMap.get("mid"));
                    }
                }
                JSONObject jsonApSystemObj1 = jsonApObj.getJSONObject("ap_net_info");
                if (null != jsonApSystemObj1) {
                    apNetDeviceDTO.setAcIp(jsonApSystemObj1.getString("ap_ip"));
                    apNetDeviceDTO.setAcSubmask(jsonApSystemObj1.getString("ap_submask"));
                    apNetDeviceDTO.setAcGateway(jsonApSystemObj1.getString("ap_gateway"));
                    apNetDeviceDTO.setAcDns(jsonApSystemObj1.getString("ap_dns"));
                    apNetDeviceDTO.setApSsid(jsonApSystemObj1.getString("ap_ssid"));
                    apNetDeviceDTO.setApRadio(jsonApSystemObj1.getString("ap_radio"));
                }
                JSONObject jsonApRunStatusObj = jsonApObj.getJSONObject("ap_run_status");
                if (jsonApRunStatusObj != null) {
                    String status = jsonApRunStatusObj.getString("ap_online_status");
                    //在线状态/1在线/0离线
                    if ("0".equals(status)) {
                        apNetDeviceDTO.setStatus("0");
                    } else {
                        apNetDeviceDTO.setStatus("1");
                    }
                    //apNetDeviceDTO.setStatus("1");
                    apNetDeviceDTO.setAcApOnlineNum(jsonApRunStatusObj.getString("ap_device_num"));
                    apNetDeviceDTO.setApIpList(jsonApRunStatusObj.getString("ap_ip_list"));
                }
                JSONObject jsonApOtherObj = jsonApObj.getJSONObject("ap_other_info");
                if (null != jsonApOtherObj) {
                    apNetDeviceDTO.setApSysTime(DateUtils.getDateByString(jsonApOtherObj.getString("ap_sys_time"), DateUtils.DATE_FORMAT));
                    apNetDeviceDTO.setApRuntime(DateUtils.getDateByString(jsonApOtherObj.getString("ap_runtime"), DateUtils.DATE_FORMAT));
                    apNetDeviceDTO.setApLicenseError(jsonApOtherObj.getString("ap_license_error"));
                }
                if (apIntegerMap.get("id") != 0) {
                    NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
                    netDeviceQuery.createCriteria().andIdEqualTo(apIntegerMap.get("id"));
                    int count = netDeviceDao.updateByExampleSelective(apNetDeviceDTO, netDeviceQuery);
                    if (count == 0) {
                        return "没有update更新网络Ap设备数据";
                    }
                } else {
                    int count1 = netDeviceDao.insertOrUpdate(apNetDeviceDTO);
                    if (count1 == 0) {
                        return "没有更新网络AP设备数据";
                    }
                }

                //ap end
                //终端
                JSONArray apStaInfos = jsonApObj.getJSONArray("ap_sta_info");
                int i = 0;
                List<String> zdMacs = new ArrayList<>();
                for (Object apStaObj : apStaInfos) {
                    JSONObject jsonApStaObj = (JSONObject) apStaObj;
                    NetDeviceDTO apStaNetDeviceDTO = new NetDeviceDTO();
                    i = i + 1;
                    String deviceSn = apNetDeviceDTO.getAcDeviceSn() + "-APSTA-" + ConvertUtils.toString(i);
                    apStaNetDeviceDTO.setAcDeviceSn(deviceSn);
                    apStaNetDeviceDTO.setId(apNetDeviceDTO.getId());
                    apStaNetDeviceDTO.setAcDeviceName(jsonApStaObj.getString("sta_name"));
                    apStaNetDeviceDTO.setAcIp(jsonApStaObj.getString("sta_ip"));
                    String mac = jsonApStaObj.getString("sta_mac").replace(":", "-").toUpperCase();
                    apStaNetDeviceDTO.setAcMac(mac);
                    zdMacs.add(mac);
                    apStaNetDeviceDTO.setStatus("1");
                    apStaNetDeviceDTO.setDeviceType(ConvertUtils.toString(DeviceTypeEnum.terminal.ordinal()));
                    apStaNetDeviceDTO.setStaRuntime(DateUtils.getDateByString(jsonApStaObj.getString("sta_runtime"), DateUtils.DATE_FORMAT));
                    apStaNetDeviceDTO.setStaSignal(ConvertUtils.toFloat(jsonApStaObj.getString("sta_signal")));
                    apStaNetDeviceDTO.setStaDataTxRate(jsonApStaObj.getString("sta_data_tx_rate"));
                    apStaNetDeviceDTO.setStaDataRxRate(jsonApStaObj.getString("sta_data_rx_rate"));
                    apStaNetDeviceDTO.setUpJson(jsonApObj.getString("ap_sta_info"));
                    Map<String, Integer> apStaIntegerMap = getIntervalByMac(mac, jsonApSystemObj.getString("ap_device_sn"));
                    apStaNetDeviceDTO.setInterval(apStaIntegerMap.get("interval"));
                    if (apIntegerMap.get("id") != 0) {
                        apStaNetDeviceDTO.setParentId(apIntegerMap.get("id"));
                    }
                    if (apIntegerMap.get("mid") != 0) {
                        apStaNetDeviceDTO.setMId(apIntegerMap.get("mid"));
                    }
                    if (apStaIntegerMap.get("id") != 0) {
                        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
                        netDeviceQuery.createCriteria().andIdEqualTo(apStaIntegerMap.get("id"));
                        int count = netDeviceDao.updateByExampleSelective(apStaNetDeviceDTO, netDeviceQuery);
                        if (count == 0) {
                            return "没有update更新网络Ap设备数据";
                        }
                    } else {
                        int count2 = netDeviceDao.insertOrUpdate(apStaNetDeviceDTO);
                        if (count2 == 0) {
                            return "没有更新网络终端设备数据";
                        }
                    }

                }
                //终端 end
                NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
                if (CollectionUtil.isNotEmpty(zdMacs)) {
                    netDeviceQuery.createCriteria().andAcMacNotIn(zdMacs)
                            .andDeviceTypeEqualTo(ConvertUtils.toString(DeviceTypeEnum.terminal.ordinal()))
                            .andParentIdEqualTo(apIntegerMap.get("id"))
                    ;
                    int count11 = netDeviceDao.deleteByExample(netDeviceQuery);
                    log.info("终端" + zdMacs + "--ap--" + apIntegerMap);
                    if (count11 == 0) {
                        log.info("没有不需要删除的终端" + zdMacs + "--ap--" + apIntegerMap);
                    }
                }
            }//end ap
        }


        return null;
    }

    @Override
    public String qjgDevUpInfo(String data) {
        if (!StringUtil.isJSON(data)) {
            return "参数不是json对象";
        }
        JSONArray jsonArray = JSONArray.parseArray(data);
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;
            CabinetDTO cabinetDTO = JSON.toJavaObject(jsonObj, CabinetDTO.class);
            if (null == cabinetDTO.getRunTime()) {
                cabinetDTO.setRunTime(cabinetDTO.getCreateTime());
            }
            int count = cabinetDao.insertOrUpdate(cabinetDTO);
            if (count == 0) {
                return "没有数据更新";
            }
        }
        return null;
    }

    /**
     * 灭火器平台上传信息
     *
     * @param data
     * @return
     */
    @Override
    public String smartDevUpInfo(String data,String stauts) throws InvocationTargetException, IllegalAccessException {
        if (!StringUtil.isJSON(data)) {
            return "参数不是json对象";
        }
        JSONObject returnJson = JSONObject.parseObject(data, Feature.OrderedField);
        Map<String, String> map = new HashMap<>();
        String msg = "";
        //设备序列号
        map.put("serialNo", returnJson.getString("serialNo"));
        //"temperature": "",//环境温度
        map.put("temperature", returnJson.getString("temperature"));
        //"humidity": "",//环境湿度
        map.put("humidity", returnJson.getString("humidity"));
        //"angle": "", //倾斜角度
        map.put("angle", returnJson.getString("angle"));
        //"power": "", //模块电量
        map.put("power", returnJson.getString("power"));
        //"geo": " $GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,*47"，//NMEA0183
        //格式解析（详见附录）
        map.put("geo", returnJson.getString("geo"));
        handleFireGeo(map);
        //"img": "",//表头图片 base64 图片内容
        map.put("img", returnJson.getString("img"));
        //"collectTime": "2023-05-02 12:00:23"，//采集时间
        map.put("collectTime", returnJson.getString("collectTime"));
        //"keepalive": “60”//通讯心跳
        map.put("keepalive", returnJson.getString("keepalive"));
        map.put("systemCode", "UP");
        map.put("upJson", data);
        map.put("stauts",stauts);
        for (String key : returnJson.keySet()) {
            if(StringUtil.isEmpty(map.get(key))){
                map.put(key,returnJson.getString(key));
            }
            //输出object.getString(key);
        }
        msg = deviceService.insertUpdate(map, DeviceTypeEnum.mhq.toString(), 0);
        return msg;
    }

    /**
     * 守护星平台上传信息
     *
     * @param data
     * @return
     */
    @Override
    public String smartSfxDevUpInfo(String data,String stauts) {
        try {
            if (!StringUtil.isJSON(data)) {
                return "参数不是json对象";
            }
            JSONObject returnJson = JSONObject.parseObject(data, Feature.OrderedField);
            Map<String, String> map = new HashMap<>();
            String msg = "";
            //设备序列号
            JSONObject thingJson=returnJson.getJSONObject("thing");
            if(thingJson!=null) {
                map.put("serialNo", thingJson.getString("deviceSn"));
            }
            //"smoke_status": "",//烟感
            map.put("smoke_status", returnJson.getString("smoke_status"));
            //"pressure": "",//气压
            map.put("pressure", returnJson.getString("pressure"));
            //"water_battery": "",//水浸电量
            map.put("water_battery", returnJson.getString("water_battery"));
            //"temperature": "", //温度
            map.put("temperature", returnJson.getString("temperature"));
            //"noise": "", //噪声
            map.put("noise", returnJson.getString("noise"));
            //湿度
            map.put("humidity", returnJson.getString("humidity"));

            //"water_status": "",//水浸
            map.put("water_status", returnJson.getString("water_status"));
           //震动
            map.put("vibrate", returnJson.getString("vibrate"));
           //人员
            map.put("people", returnJson.getString("people1"));
            //光照
            map.put("light", returnJson.getString("light"));

            //"collectTime": "1700202585538"，//采集时间
            String collectTime=DateUtils.date2Str(DateUtils.DATE_FORMAT,ConvertUtils.toDate( ConvertUtils.toLong(returnJson.getString("reportAt"))));
            map.put("collectTime", collectTime);
            map.put("systemCode", "SFXUP");
            map.put("upJson", data);
            map.put("stauts",stauts);
            for (String key : returnJson.keySet()) {
                if(StringUtil.isEmpty(map.get(key))){
                    map.put(key,returnJson.getString(key));
                }
                //输出object.getString(key);
            }
            msg = deviceService.insertUpdate(map, DeviceTypeEnum.sfx.toString(), 0);
            return msg;
        } catch (Exception e) {
            log.error("smartSfxDevUpInfo==程序错误:" + e);
            return "程序错误";
        }
    }

    @Override
    public String smartYjcDevUpInfo(CabinetDeviceDTO cabinetDeviceDTO) {
        try {
            Map<String, String> map = new HashMap<>();
            String msg = "";
            if(StringUtil.isEmpty(cabinetDeviceDTO.getCode())){
                return "没有设备编号";
            }
            map.put("deviceSn", cabinetDeviceDTO.getCode());
            //上传硬件版本号
            if(StringUtil.isNotEmpty(cabinetDeviceDTO.getDeviceHw())) {
                map.put("deviceHw", cabinetDeviceDTO.getDeviceHw());
            }
            if(StringUtil.isNotEmpty(cabinetDeviceDTO.getDeviceSw())) {
                map.put("deviceSw", cabinetDeviceDTO.getDeviceSw());
            }
            if(StringUtil.isNotEmpty(cabinetDeviceDTO.getOpHw())) {
                map.put("op_hw", cabinetDeviceDTO.getOpHw());
            }
            map.put("device_id", ConvertUtils.toString(cabinetDeviceDTO.getDeviceId()));
            map.put("cabinet_no", cabinetDeviceDTO.getCabinetNo());
            map.put("station_no",cabinetDeviceDTO.getStationNo());
            map.put("deviceName",cabinetDeviceDTO.getName());
            map.put("model", cabinetDeviceDTO.getModel());
            //湿度
            map.put("longitude", ConvertUtils.toString(cabinetDeviceDTO.getLongitude()));
            map.put("latitude", ConvertUtils.toString(cabinetDeviceDTO.getLatitude()));
            //震动
            map.put("province", cabinetDeviceDTO.getProvince());
            //人员
            map.put("city", cabinetDeviceDTO.getCity());
            //光照
            map.put("area", cabinetDeviceDTO.getArea());
            map.put("address", cabinetDeviceDTO.getAddress());
            map.put("url", cabinetDeviceDTO.getUrl());
            map.put("length", ConvertUtils.toString(cabinetDeviceDTO.getLength()));
            map.put("width", ConvertUtils.toString(cabinetDeviceDTO.getWidth()));
            map.put("hight", ConvertUtils.toString(cabinetDeviceDTO.getHight()));
            map.put("self_weight", ConvertUtils.toString(cabinetDeviceDTO.getSelfWeight()));
            map.put("support_weight", ConvertUtils.toString(cabinetDeviceDTO.getSupportWeight()));
            //"collectTime": "1700202585538"，//业务发生时间
            String collectTime=DateUtils.getYYYYMMDDHHMMSSDate(cabinetDeviceDTO.getUpdateTime());
            map.put("collectTime", collectTime);
            map.put("systemCode", "YJCUP");
            map.put("upJson", JSON.toJSONString(cabinetDeviceDTO));
            map.put("stauts",ConvertUtils.toString(cabinetDeviceDTO.getStatus()));
            msg = deviceService.insertUpdate(map, DeviceTypeEnum.yjc.toString(), 0);
            return msg;
        } catch (Exception e) {
            log.error("smartSfxDevUpInfo==程序错误:" + e);
            return "程序错误";
        }
    }

    @Override
    public String smartYjcDevUpJson(String data, String stauts) {
        try {
            if (!StringUtil.isJSON(data)) {
                return "参数不是json对象";
            }
            JSONObject returnJson = JSONObject.parseObject(data, Feature.OrderedField);
            Map<String, String> map = new HashMap<>();
            String msg = "";
            if(StringUtil.isEmpty(returnJson.getString("deviceSn"))){
                return "没有设备编号";
            }
            map.put("deviceSn", returnJson.getString("deviceSn"));
            //上传硬件版本号
            if(StringUtil.isNotEmpty(returnJson.getString("deviceHw"))) {
                map.put("deviceHw", returnJson.getString("deviceHw"));
            }
            if(StringUtil.isNotEmpty(returnJson.getString("deviceSw"))) {
                map.put("deviceSw", returnJson.getString("deviceSw"));
            }
            if(StringUtil.isNotEmpty(returnJson.getString("opHw"))) {
                map.put("op_hw", returnJson.getString("opHw"));
            }

            map.put("device_id", returnJson.getString("deviceId"));
            map.put("cabinet_no", returnJson.getString("cabinetNo"));
            map.put("station_no",returnJson.getString("stationNo"));
            map.put("deviceName",returnJson.getString("deviceName"));
            map.put("displayDeviceName",returnJson.getString("displayDeviceName"));
            map.put("displaySortorder",returnJson.getString("displaySortorder"));
            map.put("model", returnJson.getString("model"));
            //湿度
            map.put("longitude", ConvertUtils.toString(returnJson.getString("opHw")));
            map.put("latitude", ConvertUtils.toString(returnJson.getString("opHw")));
            //震动
            map.put("province",returnJson.getString("province"));
            //人员
            map.put("city", returnJson.getString("city"));
            //光照
            map.put("area", returnJson.getString("area"));
            map.put("address", returnJson.getString("address"));
            map.put("url", returnJson.getString("url"));
            map.put("length", returnJson.getString("length"));
            map.put("width", returnJson.getString("width"));
            map.put("hight", returnJson.getString("hight"));
            map.put("self_weight", returnJson.getString("selfWeight"));
            map.put("support_weight", returnJson.getString("supportWeight"));
            //"collectTime": "1700202585538"，//业务发生时间
            String collectTime=DateUtils.getYYYYMMDDHHMMSSDate(new Date());
            map.put("collectTime", collectTime);
            map.put("systemCode", "YJCUP");
            map.put("upJson", data);
            map.put("stauts",stauts);
            int mid=ConvertUtils.toInt(returnJson.getString("mId"));
            msg = deviceService.insertUpdate(map, DeviceTypeEnum.yjc.toString(), 0);
            return msg;
        } catch (Exception e) {
            log.error("smartSfxDevUpInfo==程序错误:" + e);
            return "程序错误";
        }
    }

    /**
     * 解析灭火器地理位置
     * $GPGGA,123519,4807.038,N,01131.000,E,1,08,0.9,545.4,M,46.9,M,*47
     * 在这个数据包中：
     * 标识符为"GPGGA"，代表了 GPS 定位数据。
     * 数据字段依次为时间（123519）、纬度（4807.038）、纬度方向（N）、
     * 经度 （01131.000）、经度方向（E）、定位质量指示（1）、使用卫星数量（08）、水平精度
     * 因子（0.9）、海拔高度（545.4）、高度单位（M）、大地水准面分离值（46.9）、分离
     * 单位（M）。校验和为"*47"，用于校验数据包的完整性和正确性。
     * 通过解析 NMEA 0183 数据包，接收器可以提取出各种定位、导航和时间相关的信息，以
     * 实现定位和导航功能。
     *
     * @param map
     */
    private void handleFireGeo(Map<String, String> map) {
        if (map == null || StringUtil.isEmpty(map.get("geo"))) {
            return;
        }
        String[] address = map.get("geo").split(",");
        if (address.length < 4) {
            return;
        }
        //经度
        map.put("longitude", address[4]);
        //纬度
        map.put("latitude", address[2]);
    }

    //获得更新时间
    private Map<String, Integer> getIntervalByMac(String mac, String sn) {
        Map<String, Integer> restulMap = new HashMap<>();
        Integer id = 0;
        Integer mid = 0;
        Date upTime = new Date();
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        NetDeviceQuery.Criteria criteria = netDeviceQuery.createCriteria();
        criteria.andAcMacEqualTo(mac);
        if (StringUtil.isNotEmpty(sn)) {
            criteria.andAcDeviceSnEqualTo(sn);
        }
        List<NetDeviceDTO> netDeviceDTOList = netDeviceDao.findList(netDeviceQuery);
        if (CollectionUtil.isNotEmpty(netDeviceDTOList)) {
            NetDeviceDTO netDeviceDTO = netDeviceDTOList.get(0);
            if (null != netDeviceDTO.getUpdateTime()) {
                upTime = netDeviceDTO.getUpdateTime();
            }
            if (null != netDeviceDTO.getId() && 0 != netDeviceDTO.getId()) {
                id = netDeviceDTO.getId();
            }
            if (null != netDeviceDTO.getMId() && StringUtil.isNotEmpty(netDeviceDTO.getMId())) {
                mid = netDeviceDTO.getMId();
            }
        }
        Long between = DateUtils.getLongDays(new Date(), upTime);
        long s = between / 1000;
        restulMap.put("id", id);
        restulMap.put("mid", mid);
        restulMap.put("interval", ConvertUtils.toInt(s));
        return restulMap;
    }

}
