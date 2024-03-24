package com.hdpt.device.job.jobhandler;


import cn.hutool.core.net.NetUtil;
import com.hdpt.device.dao.ICabinetDao;
import com.hdpt.device.dao.INetDeviceDao;
import com.hdpt.device.dao.IUserDao;
import com.hdpt.device.domain.Cabinet.CabinetDTO;
import com.hdpt.device.domain.Cabinet.CabinetQuery;
import com.hdpt.device.domain.hdUp.NetDeviceDTO;
import com.hdpt.device.domain.hdUp.NetDeviceQuery;
import com.hdpt.device.domain.loginVo.User;
import com.hdpt.device.domain.queryScreen.DeviceAreaVo;
import com.hdpt.device.domain.queryScreen.DeviceNameVo;
import com.hdpt.device.domain.queryScreen.DeviceProvinceVo;
import com.hdpt.device.domain.queryScreen.DeviceType;
import com.hdpt.device.type.DeviceTypeEnum;
import com.hdpt.device.utils.ConvertUtils;
import com.hdpt.device.utils.JedisUtil;
import com.hdpt.device.utils.StringUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class QueryScreenXxjob {
    private static Logger logger = LoggerFactory.getLogger(QueryScreenXxjob.class);
    @Autowired
    INetDeviceDao netDeviceDao;
    @Autowired
    ICabinetDao  cabinetDao;
    @Autowired
    IUserDao userDao;


    /**
     * 1、
     */
    @XxlJob("screenDataJobHandler")
    public void screenDataJobHandler() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        //String localMacAddress = NetUtil.getMacAddress(inetAddress).replace("-","");
        String localMacAddress =inetAddress.getHostName();
        //1
        XxlJobHelper.log("大屏查询设备分布数据开始.....主机："+localMacAddress);
        List<DeviceProvinceVo> list =getProvinceDeviceNum();
        logger.info("list---"+list);
        //String str= JSON.toJSONString(list);
        JedisUtil.setList(localMacAddress+"_deviceProvince",list);
        /*for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("查询循环 at:" + i);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("test"+new Date());
        }*/
        XxlJobHelper.log("大屏查询设备分布结束数据"+list);
        //2
        XxlJobHelper.log("大屏查询设备地图数据开始....");
        List<DeviceAreaVo> listArea =getAreaDeviceNum();
        JedisUtil.setList(localMacAddress+"_deviceArea",listArea);
        XxlJobHelper.log("大屏查询设备地图数据结束...."+listArea);
        //3 访客数
        XxlJobHelper.log("大屏查询设离线状态数量开始....");
        Map<String,Object> map=getLineDate();
        JedisUtil.setMap(localMacAddress+"_lineStatus",map);
        XxlJobHelper.log("大屏查询设离线状态数量结束...."+map);

        //4 获得设备类型名称数量
        XxlJobHelper.log("大屏查询获得设备类型名称数量开始....");
        List<DeviceType> typeList= getDeviceTypeNum();
        JedisUtil.setList(localMacAddress+"_deviceType",typeList);
        XxlJobHelper.log("大屏查询获得设备类型名称数量结束...."+typeList);

        //5 条件查询最新设备的状态和位置
        XxlJobHelper.log("大屏查询获得查询最新设备的状态和位置开始....");
        List<DeviceNameVo> nameList= getDeviceNameNum();
        JedisUtil.setList(localMacAddress+"_deviceName",nameList);
        XxlJobHelper.log("大屏查询获得查询最新设备的状态和位置结束...."+nameList);
    }

    private  List<DeviceNameVo> getDeviceNameNum(){
        List<DeviceNameVo> nameVoList=new ArrayList<>();
        CabinetQuery cabinetQuery =new CabinetQuery();
        int pageNum=1,pageSize=1000;

            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            cabinetQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        String sortStr = "update_time desc";

        cabinetQuery.setOrderByClause(sortStr);
        List<?>  list=cabinetDao.getPageByExample(cabinetQuery);
        List<CabinetDTO> cabinetDTOS = (List<CabinetDTO>) list.get(0);
        for(CabinetDTO cabinetDTO:cabinetDTOS){
            DeviceNameVo deviceNameVo=new DeviceNameVo();
            if(StringUtil.isNotEmpty(cabinetDTO.getCabinetName())){
                deviceNameVo.setName(cabinetDTO.getCabinetName());
            }else{
                deviceNameVo.setName("");
            }
            StringBuilder sb=new StringBuilder();
            if(StringUtil.isNotEmpty(cabinetDTO.getProvince())){
                sb.append(cabinetDTO.getProvince()+"-");
            }
            if(StringUtil.isNotEmpty(cabinetDTO.getCity())){
                sb.append(cabinetDTO.getCity()+"-");
            }
            if(StringUtil.isNotEmpty(cabinetDTO.getArea())){
                sb.append(cabinetDTO.getArea()+"-");
            }
            if(sb.length()>0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            if(null!=cabinetDTO.getCabinetStatus() && "1".equals(cabinetDTO.getCabinetStatus())) {
                deviceNameVo.setStatus("在线");
            }else{
                deviceNameVo.setStatus("离线");
            }
            deviceNameVo.setArea(sb.toString());
            deviceNameVo.setMId(cabinetDTO.getMId());
            deviceNameVo.setUpdateTime(cabinetDTO.getUpdateTime());
            if(StringUtils.isNotEmpty(deviceNameVo.getName())) {
                nameVoList.add(deviceNameVo);
            }
        }

        //获得网络设备
        NetDeviceQuery netDeviceQuery = new NetDeviceQuery();
        netDeviceQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        netDeviceQuery.setOrderByClause(sortStr);
        List<?>  netList=netDeviceDao.getPageByExample(netDeviceQuery);
        List<NetDeviceDTO> NetDeviceDTOS = (List<NetDeviceDTO>) netList.get(0);
        for(NetDeviceDTO item:NetDeviceDTOS){
            DeviceNameVo deviceNameVo=new DeviceNameVo();
            if(StringUtil.isNotEmpty(item.getAcDeviceName())){
                deviceNameVo.setName(item.getAcDeviceName());
            }else{
                deviceNameVo.setName("");
            }
            StringBuilder sb=new StringBuilder();
            if(StringUtil.isNotEmpty(item.getProvince())){
                sb.append(item.getProvince()+"-");
            }
            if(StringUtil.isNotEmpty(item.getCity())){
                sb.append(item.getCity()+"-");
            }
            if(StringUtil.isNotEmpty(item.getArea())){
                sb.append(item.getArea()+"-");
            }
            if(sb.length()>0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            if(null!=item.getStatus() && "1".equals(item.getStatus())) {
                deviceNameVo.setStatus("在线");
            }else{
                deviceNameVo.setStatus("离线");
            }
            deviceNameVo.setArea(sb.toString());
            deviceNameVo.setMId(item.getMId());
            deviceNameVo.setUpdateTime(item.getUpdateTime());
            if(StringUtils.isNotEmpty(deviceNameVo.getName())) {
                nameVoList.add(deviceNameVo);
            }
        }
        return nameVoList;
    }

    /**
     * 获得在线设备类型数量
     * @return
     */
    private  List<DeviceType>  getDeviceTypeNum(){
        List<DeviceType> typeList=new ArrayList<>();
        List<CabinetDTO>  cabinetDTOList=cabinetDao.findListByWhere(new CabinetQuery());
        Map<Integer, List<CabinetDTO>> maplist = cabinetDTOList.stream().collect(Collectors.groupingBy(item -> item.getMId()));
        //分组后求总数量
        maplist.keySet().stream().forEach(key->{
            DeviceType info = new DeviceType();
            List<CabinetDTO> listInfo = maplist.get(key);
            //总数量
            Integer sumCount = listInfo.size();
            info.setType(DeviceTypeEnum.qjg.toString());
            info.setMId(key);
            info.setDeviceNum(sumCount);
            typeList.add(info);
        });
        List<NetDeviceDTO> netDeviceDTOList=netDeviceDao.findTypeCount();
       for(NetDeviceDTO netDeviceDTO:netDeviceDTOList){
           DeviceType deviceType = new DeviceType();
           deviceType.setMId(netDeviceDTO.getMId());
           int type=ConvertUtils.toInt(netDeviceDTO.getDeviceType());
           switch (type) {
               case 1:
                   deviceType.setType(DeviceTypeEnum.wifi.toString());
                   deviceType.setDeviceNum(netDeviceDTO.getCount());
                   break;
               case 2:
                   deviceType.setType(DeviceTypeEnum.ap.toString());
                   deviceType.setDeviceNum(netDeviceDTO.getCount());
                   break;
               case 3:
                   deviceType.setType(DeviceTypeEnum.gateway.toString());
                   deviceType.setDeviceNum(netDeviceDTO.getCount());
                   break;
               case 4:
                   deviceType.setType(DeviceTypeEnum.hightGateway.toString());
                   deviceType.setDeviceNum(netDeviceDTO.getCount());
                   break;
               default:
                   deviceType.setType(DeviceTypeEnum.no.toString());
                   deviceType.setDeviceNum(netDeviceDTO.getCount());
                   break;
           }
           typeList.add(deviceType);
       }

        return typeList;
    }

    /**
     * 获得在线在状态数量，包在线人数和注册人数。
     * @return
     */
    private  Map<String,Object> getLineDate(){
        Map<String,Object> map =new HashMap<>() ;
        int lineNum=0;
        if(JedisUtil.get("onlineNum")!=null){
            lineNum=ConvertUtils.toInt(JedisUtil.get("onlineNum"));
        }
        Integer onlineNum= lineNum;
        map.put("onlineUser",onlineNum);
        List<User>  users=userDao.findList();
        Integer regUserNum=0;
        if(CollectionUtils.isNotEmpty(users)){
            regUserNum=users.size();
        }
        map.put("regUser",regUserNum);
        //map.put("onlineUser",onlineNum);
        List<NetDeviceDTO> netDeviceDTOList=netDeviceDao.findStatusCount();
        List<CabinetDTO>  cabinetDTOList=cabinetDao.findStatusCount();
        //合并到netDeviceDTOList
        for (CabinetDTO cabinetDTO:cabinetDTOList){
            NetDeviceDTO netDeviceDTO=new NetDeviceDTO();
            if(null==cabinetDTO.getMId()) {
                netDeviceDTO.setMId(0);
            }else{
                netDeviceDTO.setMId(cabinetDTO.getMId());
            }
            netDeviceDTO.setStatus(cabinetDTO.getCabinetStatus());
            netDeviceDTO.setCount(cabinetDTO.getCount());
            netDeviceDTOList.add(netDeviceDTO);
        }
        //求合
        List<NetDeviceDTO> resultList = new ArrayList<>();
        //先根据mid,status分组
        Map<String, List<NetDeviceDTO>> maplist = netDeviceDTOList.stream().collect(Collectors.groupingBy(item -> item.getMId() + "_" + item.getStatus()));
        //分组后求总数量
        maplist.keySet().stream().forEach(key->{
            NetDeviceDTO info = new NetDeviceDTO();
            List<NetDeviceDTO> listInfo = maplist.get(key);
            //总数量
            Integer sumCount = listInfo.stream().map(e -> e.getCount()).reduce(Integer::sum).get();
            String [] keys=key.split("_");
            if(keys!=null && keys.length>0) {
                if(null!=keys[0] && StringUtil.isNumeric(keys[0])) {
                    info.setMId(ConvertUtils.toInt(keys[0]));
                }
                if(StringUtil.isNotEmpty(keys[1])) {
                    info.setStatus(keys[1]);
                    info.setCount(sumCount);
                    resultList.add(info);
                }
            }
        });
        List<Map<String,Integer>> mapList=new ArrayList<>();
        for(NetDeviceDTO netDeviceDTO:resultList) {
            Map<String,Integer> statusMap=new HashMap<>();
            statusMap.put("mId", netDeviceDTO.getMId());
            if("1".equals(netDeviceDTO.getStatus())) {
                statusMap.put("online", netDeviceDTO.getCount());
            }else {
                statusMap.put("offline", netDeviceDTO.getCount());
            }
            mapList.add(statusMap);
        }
        map.put("onlineDevice",mapList);
        return map;
    }

    //查询设备分布数据
    private  List<DeviceProvinceVo> getProvinceDeviceNum(){
        List<DeviceProvinceVo> list =new ArrayList<>();
        List<NetDeviceDTO> netDeviceDTOS=netDeviceDao. findProvinceCount();
        List<CabinetDTO>  cabinetDTOS=cabinetDao.findProvinceCount();
        for(NetDeviceDTO netDeviceDTO:netDeviceDTOS){
            DeviceProvinceVo deviceProvinceVo=new DeviceProvinceVo();
            int count = netDeviceDTO.getCount();
            List<CabinetDTO>  cabinetFilters=new ArrayList<>(cabinetDTOS);
            CollectionUtils.filter(cabinetFilters, cabinetDTO -> {
                if(StringUtil.isEmpty(cabinetDTO.getProvince()) && StringUtil.isEmpty(netDeviceDTO.getProvince())
                        && cabinetDTO.getMId()==netDeviceDTO.getMId()){
                   return true;
                }
                else if(StringUtil.isNotEmpty(cabinetDTO.getProvince())
                        && StringUtil.isNotEmpty(netDeviceDTO.getProvince())
                        && cabinetDTO.getProvince().equals(netDeviceDTO.getProvince())
                        && cabinetDTO.getMId()==netDeviceDTO.getMId()) {
                    return true;
                }else{
                    return false;
                }
            });
            if(CollectionUtils.isNotEmpty(cabinetFilters)) {
                count = count + cabinetFilters.get(0).getCount();
            }
            deviceProvinceVo.setProvince(netDeviceDTO.getProvince());
            deviceProvinceVo.setDeviceNum(count);
            deviceProvinceVo.setMId(netDeviceDTO.getMId());
            list.add(deviceProvinceVo);

        }
        return  list;
    }

    /**
     * 设备地图数据
     * @return
     */
    private  List<DeviceAreaVo> getAreaDeviceNum(){
        List<DeviceAreaVo> list = new ArrayList<>();
        List<NetDeviceDTO> netDeviceDTOS=netDeviceDao.findAreaCount();
        List<CabinetDTO>  cabinetDTOS=cabinetDao.findAreaCount();

        for(NetDeviceDTO netDeviceDTO:netDeviceDTOS){
            DeviceAreaVo deviceAreaVo=new DeviceAreaVo();
            List<CabinetDTO>  cabinetFilters=new ArrayList<>(cabinetDTOS);
            int count=netDeviceDTO.getCount();
            CollectionUtils.filter(cabinetFilters, cabinetDTO -> {
                if(StringUtil.isEmpty(netDeviceDTO.getProvince())
                        || StringUtil.isEmpty(netDeviceDTO.getCity())
                        || StringUtil.isEmpty(netDeviceDTO.getArea())){
                    return false;
                }
                else if(cabinetDTO.getProvince().equals(netDeviceDTO.getProvince())
                        && cabinetDTO.getMId()==netDeviceDTO.getMId()
                        && cabinetDTO.getCity().equals(netDeviceDTO.getCity())
                        && cabinetDTO.getArea().equals(cabinetDTO.getArea())) {
                    return true;
                }else{
                    return false;
                }
            });
            if(CollectionUtils.isNotEmpty(cabinetFilters)) {
                count = count + cabinetFilters.get(0).getCount();
            }
            deviceAreaVo.setProvince(netDeviceDTO.getProvince());
            deviceAreaVo.setCity(netDeviceDTO.getCity());
            deviceAreaVo.setArea(netDeviceDTO.getArea());
            deviceAreaVo.setDeviceNum(count);
            deviceAreaVo.setMId(netDeviceDTO.getMId());
            list.add(deviceAreaVo);
        }
        return list;
    }

}
