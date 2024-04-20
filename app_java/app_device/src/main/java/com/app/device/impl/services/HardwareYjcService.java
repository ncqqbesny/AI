package com.app.device.impl.services;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.app.device.config.MqttProviderConfig;
import com.app.device.dao.IBitCtrlDao;
import com.app.device.domain.BitCtrl.BitCtrlDTO;
import com.app.device.domain.BitCtrl.BitCtrlQuery;
import com.app.device.domain.BitCtrl.BitCtrlVo;
import com.app.device.services.IHardwareCabService;
import com.app.device.services.IHardwareYjcService;
import com.app.device.type.OpTypeEnum;
import com.app.device.type.RequestFlagEnum;
import com.app.device.utils.CommonPage;
import com.app.device.utils.ConvertUtils;
import com.app.device.utils.DateUtils;
import com.app.device.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HardwareYjcService implements IHardwareYjcService {
    private final static Logger log = LoggerFactory.getLogger(HardwareYjcService.class);
    @Autowired
    IBitCtrlDao bitCtrlDao;
    @Autowired
    IHardwareCabService hardwareCabService;
    @Autowired
    private MqttProviderConfig providerClient;
    @Value("${spring.mqtt.op.topic}")
    private String opTopic;


    /**
     * 界面操作点位按钮
     *
     * @param bitCtrlVos
     * @return
     */
    @Override
    public String saveBitCtrl(List<BitCtrlVo> bitCtrlVos,String operType)  {
        List<BitCtrlDTO> bitCtrlDTOListMqtt=new ArrayList<>();
        for (BitCtrlVo item : bitCtrlVos) {
            if(StringUtil.isEmpty(item.getDeviceSn())){
                return "没有设备编号参数";
            }
            //查询
            BitCtrlVo bitCtrlVo=new BitCtrlVo();
            bitCtrlVo.setDeviceSn(item.getDeviceSn());
            bitCtrlVo.setCtrlNo(item.getCtrlNo());
            bitCtrlVo.setAddress(item.getAddress());
            bitCtrlVo.setCtrlNum(item.getCtrlNum());
            bitCtrlVo.setCtrlType(item.getCtrlType());
            //查询所得数据
            List<BitCtrlDTO> bitCtrlDTOS=new ArrayList<>();
            bitCtrlDTOS=getBitCtrlDtos(bitCtrlVo);
            if(CollectionUtil.isEmpty(bitCtrlDTOS)){
                bitCtrlVo.setDeviceSn(null);
                bitCtrlDTOS=getBitCtrlDtos(bitCtrlVo);
            }
            BitCtrlDTO bitCtrlDTO = new BitCtrlDTO();
            if(CollectionUtil.isNotEmpty(bitCtrlDTOS)){
                BeanUtil.copyProperties(bitCtrlDTOS.get(0), bitCtrlDTO);
            }
            //空值不复制
            final CopyOptions copyOptions = CopyOptions.create();
            copyOptions.setIgnoreNullValue(true);
            BeanUtil.copyProperties(item, bitCtrlDTO,copyOptions);
            bitCtrlDTO.setStatus(1);
            bitCtrlDTO.setRequestFlag(RequestFlagEnum.start.ordinal());
            bitCtrlDTO.setRequestNo("M" + UUID.randomUUID().toString().replace("-", ""));
            bitCtrlDTO.setUpdateTime(new Date());
            bitCtrlDTOListMqtt.add(bitCtrlDTO);
            int count = bitCtrlDao.insertOrUpdate(bitCtrlDTO);
            if (count == 0) {
                return "没有数据更新";
            }
        }
        String msg="";
        //根据操作设备编号进行分发mqtt
        if(operType.equals(OpTypeEnum.op.name()) || operType.equals(OpTypeEnum.manual.name())) {
            log.info("手工执行操作开始发送mqtt信息========" + JSON.toJSONString(bitCtrlDTOListMqtt));
            msg = sendBitCtrlInfoByMqtt(bitCtrlDTOListMqtt,opTopic);
            log.info("手工执行操作结束发送mqtt信息");
        }

        if (StringUtil.isNotEmpty(msg)) {
            log.warn("单次操作信息:" + msg);
        }
        return "";
    }

    /**
     * 发送操作mqtt
     * @param bitCtrlDTOListMqtt
     * @param mqttTopic
     * @return
     */
    public String sendBitCtrlInfoByMqtt(List<BitCtrlDTO> bitCtrlDTOListMqtt,String mqttTopic){
        String msg="";
        int qos=1;
        boolean retained=true;
        if(StringUtil.isEmpty(mqttTopic)){
            return "mqtt-topic为空";
        }
        if(CollectionUtil.isEmpty(bitCtrlDTOListMqtt)){
            return "没有数据信息，不发送信息";
        }
        String message= JSON.toJSONString(bitCtrlDTOListMqtt);
        providerClient.connect();
        providerClient.publish(qos, retained, mqttTopic, message);
        return msg;
    }

    /**
     * 获取批次的查询条件
     *
     * @return
     */
    private BitCtrlQuery getBatchOperBitQuery(BitCtrlVo param, List<BitCtrlVo> bitCtrlVos, Integer requestFlag) {
        BitCtrlQuery bitCtrlQuery = new BitCtrlQuery();
        BitCtrlQuery.Criteria criteria = bitCtrlQuery.createCriteria();
        if (RequestFlagEnum.batch.ordinal() == requestFlag || RequestFlagEnum.closeLock.ordinal() == requestFlag) {
            criteria.andRequestFlagEqualTo(requestFlag);
        }
        if (RequestFlagEnum.auto.ordinal() == requestFlag) {
            if (CollectionUtil.isEmpty(bitCtrlVos)) {
                criteria.andRequestFlagEqualTo(requestFlag);
                return bitCtrlQuery;
            }
            List<String> cabinetNos = new ArrayList<>();
            List<String> stationNos = new ArrayList<>();
            List<Integer> addresses = new ArrayList<>();
            List<Integer> ctrlNums = new ArrayList<>();
            List<Integer> ctrTypes = new ArrayList<>();
            for (BitCtrlVo bitCtrlVo : bitCtrlVos) {
                if (StringUtil.isNotEmpty(bitCtrlVo.getCabinetNo())) {
                    cabinetNos.add(bitCtrlVo.getCabinetNo());
                }
                if (StringUtil.isNotEmpty(bitCtrlVo.getStationNo())) {
                    stationNos.add(bitCtrlVo.getStationNo());
                }
                if (StringUtil.isNotEmpty(bitCtrlVo.getAddress())) {
                    addresses.add(bitCtrlVo.getAddress());
                }
                if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlNum())) {
                    ctrlNums.add(bitCtrlVo.getCtrlNum());
                }
                if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlType())) {
                    ctrTypes.add(bitCtrlVo.getCtrlType());
                }
            }
            if (CollectionUtil.isNotEmpty(cabinetNos)) {
                criteria.andCabinetNoIn(cabinetNos);
            }
            if (CollectionUtil.isNotEmpty(stationNos)) {
                criteria.andStationNoIn(stationNos);
            }
            if (CollectionUtil.isNotEmpty(addresses)) {
                criteria.andAddressIn(addresses);
            }
            if (CollectionUtil.isNotEmpty(ctrlNums)) {
                criteria.andCtrlNumIn(ctrlNums);
            }
            if (CollectionUtil.isNotEmpty(ctrTypes)) {
                criteria.andCtrlTypeIn(ctrTypes);
            }

        } else {
            if (StringUtil.isEmpty(param.getCtrlNo())) {
                criteria.andStatusEqualTo(1);
                log.info("自动开始执行请求编号：" + param.getRequestNo());
            } else {
                log.info("手工开始执行请求编号：" + param.getRequestNo());
                criteria.andCtrlNoEqualTo(param.getCtrlNo());
            }
            if (StringUtil.isNotEmpty(param.getCabinetNo())) {
                criteria.andCabinetNoEqualTo(param.getCabinetNo());
            }
            if (StringUtil.isNotEmpty(param.getStationNo())) {
                criteria.andStationNoEqualTo(param.getStationNo());
            }
            if (StringUtil.isNotEmpty(param.getSignalingBit())) {
                criteria.andSignalingBitEqualTo(param.getSignalingBit());
            }
            if (CollectionUtil.isNotEmpty(bitCtrlVos)) {
                List<String> ctrNos = new ArrayList<>();
                List<String> signalingBits = new ArrayList<>();
                for (BitCtrlVo bitCtrlVo : bitCtrlVos) {
                    if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlNo())) {
                        ctrNos.add(bitCtrlVo.getCtrlNo());
                    }
                    if (StringUtil.isNotEmpty(bitCtrlVo.getSignalingBit())) {
                        signalingBits.add(bitCtrlVo.getSignalingBit());
                    }
                }
                if (CollectionUtil.isNotEmpty(ctrNos)) {
                    criteria.andCtrlNoIn(ctrNos);
                }
                if (CollectionUtil.isNotEmpty(signalingBits)) {
                    criteria.andSignalingBitIn(signalingBits);
                }
            }
        }
        String sortStr = "address desc";
        bitCtrlQuery.setOrderByClause(sortStr);
        return bitCtrlQuery;
    }






    /**
     * 逻辑处理批量保存
     */
    class batchSaveBitCtrlTask implements Runnable {
        private List<BitCtrlDTO> bitCtrlDoS;

        public batchSaveBitCtrlTask(List<BitCtrlDTO> bitCtrlDoS) {
            this.bitCtrlDoS = bitCtrlDoS;
        }

        @Override
        public void run() {
            log.info("开始用线程池逻辑批量更新执行线程--");
            int count = bitCtrlDao.updateBatch(bitCtrlDoS);
            if (count == 0) {
                log.info("逻辑批量无数据更新执行--" + count);
            }
            log.info("结束用线程池逻辑批量更新执行线程" + UUID.randomUUID());
        }
    }





    /**
     * 联动处理控制点位
     *
     * @param bitCtrlDTO
     */
    private String handleBitCtrl(BitCtrlDTO bitCtrlDTO) {
        BitCtrlQuery bitCtrlQuery = new BitCtrlQuery();
        BitCtrlQuery.Criteria criteria = bitCtrlQuery.createCriteria();
        if (StringUtil.isNotEmpty(bitCtrlDTO.getCabinetNo())) {
            criteria.andCabinetNoEqualTo(bitCtrlDTO.getCabinetNo());
        }
        if (StringUtil.isNotEmpty(bitCtrlDTO.getStationNo())) {
            criteria.andStationNoEqualTo(bitCtrlDTO.getStationNo());
        }
        if (StringUtil.isNotEmpty(bitCtrlDTO.getType())) {
            criteria.andTypeEqualTo(bitCtrlDTO.getType());
        }
        if (StringUtil.isNotEmpty(bitCtrlDTO.getAddress())) {
            criteria.andAddressEqualTo(bitCtrlDTO.getAddress());
        }
        if (StringUtil.isNotEmpty(bitCtrlDTO.getCtrlType())) {
            criteria.andCtrlTypeEqualTo(bitCtrlDTO.getCtrlType());
        }
        if (StringUtil.isNotEmpty(bitCtrlDTO.getCtrlNum())) {
            criteria.andCtrlNumEqualTo(bitCtrlDTO.getCtrlNum());
        }
        int count = bitCtrlDao.updateByExampleSelective(bitCtrlDTO, bitCtrlQuery);
        if (count == 0) {
            return "请求编号：" + bitCtrlDTO.getRequestNo() + "没有对应的数据进行更新";
        }
        return "";
    }

    @Override
    public String saveListInfo(List<BitCtrlVo> bitCtrlVos) {
        for (BitCtrlVo bitCtrlVo : bitCtrlVos) {
            BitCtrlDTO bitCtrlDTO = new BitCtrlDTO();
            BeanUtil.copyProperties(bitCtrlVo, bitCtrlDTO);
            int count = bitCtrlDao.insertOrUpdate(bitCtrlDTO);
            if (count == 0) {
                return "没有保存成功,请检查保存数据";
            }
        }
        return null;
    }

    @Override
    public String delInfo(List<Integer> ids) {
        BitCtrlQuery bitCtrlQuery = new BitCtrlQuery();
        if (CollectionUtil.isEmpty(ids)) {
            return "没有删除id";
        }
        bitCtrlQuery.createCriteria().andIdIn(ids);
        int count = bitCtrlDao.deleteByExample(bitCtrlQuery);
        if (count == 0) {
            return "没有删除成功,请检查删除数据";
        }
        return null;
    }


    ;

    @Override
    public CommonPage<BitCtrlDTO> getBitCtrlPageList(Integer pageSize, Integer pageNum, Integer sort, BitCtrlVo bitCtrlVo) {
        BitCtrlQuery bitCtrlQuery = getBitCtrlQuery(pageSize, pageNum, sort, bitCtrlVo);
        List<?> list = bitCtrlDao.getPageByExample(bitCtrlQuery);
        List<BitCtrlDTO> bitCtrlDTOList = (List<BitCtrlDTO>) list.get(0);
        Long total = ((List<Long>) list.get(1)).get(0);
        return CommonPage.restPage(bitCtrlDTOList, pageNum, pageSize, total);
    }

    @Override
    public Map<String, String> getBitCtrlList(BitCtrlVo bitCtrlVo) {
        List<BitCtrlDTO> bitCtrlDTOList = getBitCtrlDtos(bitCtrlVo);
        Integer total = bitCtrlDTOList.size();
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("totalNum", ConvertUtils.toString(total));
        resultMap.put("resultCaption", "key 为信号位，value为开和关(0、ON，2.OFF，1.模拟数量");
        for (BitCtrlDTO bitCtrlDTO : bitCtrlDTOList) {
            resultMap.put("deviceSn",  bitCtrlDTO.getDeviceSn());
            resultMap.put("cabinetNo", bitCtrlDTO.getCabinetNo());
            resultMap.put("stationNo", bitCtrlDTO.getStationNo());
            String key = bitCtrlDTO.getSignalingBit();
            if (StringUtil.isEmpty(key)) {
                continue;
            }
            if (bitCtrlDTO.getCtrlType() != null && (bitCtrlDTO.getCtrlType() == 2 || bitCtrlDTO.getCtrlType() == 3)) {
                resultMap.put(key, bitCtrlDTO.getAnalogNum());
            } else {
                resultMap.put(key, ConvertUtils.toString(bitCtrlDTO.getType()));
            }
        }
        return resultMap;
    }
    /**
     * 获得控制点位的查询数据
     *
     * @param bitCtrlVo
     * @return
     */
    public List<BitCtrlDTO> getBitCtrlDtos(BitCtrlVo bitCtrlVo) {
        List<BitCtrlDTO> bitCtrlDTOS = new ArrayList<>();
        BitCtrlQuery bitCtrlQuery = getBitCtrlQuery(null, null, null, bitCtrlVo);
        List<?> list = bitCtrlDao.getPageByExample(bitCtrlQuery);
        bitCtrlDTOS = (List<BitCtrlDTO>) list.get(0);
        return bitCtrlDTOS;
    }


    private BitCtrlQuery getBitCtrlQuery(Integer pageSize, Integer pageNum, Integer sort, BitCtrlVo bitCtrlVo) {
        BitCtrlQuery bitCtrlQuery = new BitCtrlQuery();
        BitCtrlQuery.Criteria criteria = bitCtrlQuery.createCriteria();
        //设备编号
        if (StringUtil.isNotEmpty(bitCtrlVo.getDeviceSn())) {
            criteria.andDeviceSnEqualTo(bitCtrlVo.getDeviceSn());
        }else{
            criteria.andDeviceSnIsNull();
        }
        //调用编号
        if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlNo())) {
            criteria.andCtrlNoLike(bitCtrlVo.getCtrlNo());
        }
        //类型描述
        if (StringUtil.isNotEmpty(bitCtrlVo.getTypeDesc())) {
            criteria.andTypeDescLike(bitCtrlVo.getTypeDesc());
        }
        //备注
        if (StringUtil.isNotEmpty(bitCtrlVo.getRemark())) {
            criteria.andRemarkLike(bitCtrlVo.getRemark());
        }
        //启用状态
        if (StringUtil.isNotEmpty(bitCtrlVo.getStatus())) {
            criteria.andStatusEqualTo(bitCtrlVo.getStatus());
        }
        //地址类型
        if (StringUtil.isNotEmpty(bitCtrlVo.getAddressType())) {
            criteria.andAddressTypeEqualTo(bitCtrlVo.getAddressType());
        }
        //地址
        if (StringUtil.isNotEmpty(bitCtrlVo.getAddress())) {
            criteria.andAddressEqualTo(bitCtrlVo.getAddress());
        }
        if (CollectionUtil.isNotEmpty(bitCtrlVo.getAddresses())) {
            criteria.andAddressIn(bitCtrlVo.getAddresses());
        }
        //信号类型：0、ON，1、模拟量 2.OFF
        if (StringUtil.isNotEmpty(bitCtrlVo.getType())) {
            criteria.andTypeEqualTo(bitCtrlVo.getType());
        }
        //控制类型 0.DO,1.DI
        if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlType())) {
            criteria.andCtrlTypeEqualTo(bitCtrlVo.getCtrlType());
        }
        if (CollectionUtil.isNotEmpty(bitCtrlVo.getCtrlTypes())) {
            criteria.andCtrlTypeIn(bitCtrlVo.getCtrlTypes());
        }
        //don 或 din
        if (StringUtil.isNotEmpty(bitCtrlVo.getCtrlNum())) {
            criteria.andCtrlNumEqualTo(bitCtrlVo.getCtrlNum());
        }
        if (CollectionUtil.isNotEmpty(bitCtrlVo.getCtrlNums())) {
            criteria.andCtrlNumIn(bitCtrlVo.getCtrlNums());
        }
        //柜号
        if (StringUtil.isNotEmpty(bitCtrlVo.getCabinetNo())) {
            criteria.andCabinetNoEqualTo(bitCtrlVo.getCabinetNo());
        }
        //站号
        if (StringUtil.isNotEmpty(bitCtrlVo.getStationNo())) {
            criteria.andStationNoEqualTo(bitCtrlVo.getStationNo());
        }
        //信号点位
        if (StringUtil.isNotEmpty(bitCtrlVo.getSignalingBit())) {
            criteria.andSignalingBitEqualTo(bitCtrlVo.getSignalingBit());
        }
        //请求处理标
        if (StringUtil.isNotEmpty(bitCtrlVo.getRequestFlag())) {
            criteria.andRequestFlagEqualTo(bitCtrlVo.getRequestFlag());
        }
        if (null != pageNum && null != pageSize) {
            String fromRow = ConvertUtils.toString((pageNum - 1) * pageSize);
            bitCtrlQuery.setPageNumAndSize(fromRow + ","
                    + ConvertUtils.toString(pageSize));
        }
        if (null != sort) {
            //排序 0，降序，1，升序
            String sortStr = "if(`status` is NULL OR `status`='' ,'0',`status`),create_time";
            if (sort == 0) {
                sortStr = "if(`status` is NULL OR `status`='' ,'0',`status`) desc,create_time desc";
            }
            bitCtrlQuery.setOrderByClause(sortStr);
        }
        return bitCtrlQuery;
    }


}
