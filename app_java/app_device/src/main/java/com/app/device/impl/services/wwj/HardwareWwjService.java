package com.app.device.impl.services.wwj;

import cn.hutool.core.collection.CollectionUtil;
import com.app.device.dao.IDtuCmdDao;
import com.app.device.domain.Wwj.DtuCmdDTO;
import com.app.device.domain.Wwj.DtuCmdPO;
import com.app.device.domain.Wwj.DtuCmdVO;
import com.app.device.handler.wwj.ClientSendSocket;
import com.app.device.handler.wwj.ServerThread;
import com.app.device.services.wwj.IHardwareWwjService;
import com.app.device.type.DtuCmdStatusEnum;
import com.app.device.utils.ListUtils;
import com.app.device.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HardwareWwjService implements IHardwareWwjService {
    private final static Logger log = LoggerFactory.getLogger(HardwareWwjService.class);
    @Value("${server.socket.port}")
    private Integer port;
    @Autowired
    IDtuCmdDao dtuCmdDao;

    /**
     * 打开长连接端口
     *
     * @param
     * @return
     */
    @Override
    public String openHardPort() {
        try {
            //Connect.longConnection();

            log.info(">>>服务启动,等待终端的连接\n");
            if (null == port) {
                port = 8838;
            }
            ServerSocket server = new ServerSocket(port);
            int count = 0;
            while (true) {
                //开启监听
                Socket socket = server.accept();
                count++;
                log.info(">>>开启第" + count + "次长连接...");
                ServerThread thread = new ServerThread(socket);
                thread.start();
                return "打开长连接端口" + port;
            }
        } catch (Exception e) {
            log.error("开启长连接错误");
            e.printStackTrace();
            return "打开端口" + port + "失败";
        }

    }
    @Override
    public Map<String, Object> getPortInfoAndProc() {
        try {
            Socket client = new Socket("localhost", port);
            OutputStream out = client.getOutputStream();
            try {
                while (true) {
                    Thread.sleep(3000);
                    System.out.println("发送心跳数据包");
                    out.write("send heart beat data package !".getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.close();
                client.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String openAndCloseHard(Integer cent) {
        String adddata1 = "{\"action\":\"sendc\",\"data\":\"config,set,doout,1,1\\r\\n\",\"hex\":false,\"client\":\"aioSession--1427695944\"}";
        String addData = "config,set,doout,1,1\\r\\n";
        String minusData1 = "{\"action\":\"sendc\",\"data\":\"config,set,doout,1,0\\r\\n\",\"hex\":false,\"client\":\"aioSession--1427695944\"}";
        String minusData = "config,set,doout,1,0\\r\\n";
        try {
            ClientSendSocket client = new ClientSendSocket("localhost", port);
            // 发送多次数据
            client.sendData("Hello, server!");
            client.sendData("Another message.");
            client.sendData("Last message.");

            if (cent != null && cent > 0) {
                for (int i = 0; i < cent; i++) {
                    client.sendData(addData);
                    client.sendData(minusData);
                }
                client.close();
            } else {
                client.close();
                return "上分不能为负数";
            }
        } catch (Exception e) {
            log.error("发送数据错误" + e);
            return "发送数据错误";
        }

        return null;
    }

    /**
     * 保存数据
     * @param dtuCmdDto
     * @return
     */
    @Override
    public String saveDtuCmd(DtuCmdDTO dtuCmdDto) {
        LambdaQueryWrapper<DtuCmdPO> queryWrapper = getQueryWrapper(1, dtuCmdDto);
        List<DtuCmdPO> dtuCmdPOS =dtuCmdDao.selectList(queryWrapper);
        int count=0;
        if(CollectionUtil.isNotEmpty(dtuCmdPOS)){
            count=dtuCmdDao.update(dtuCmdDto,queryWrapper);
        }else {
            count=dtuCmdDao.insert(dtuCmdDto);
        }
        if(count==0){
            return "保存错误";
        }
        return "";
    }

    @Override
    public List<DtuCmdVO> getDtuCmdList(DtuCmdDTO dtuCmdDto) {
        LambdaQueryWrapper<DtuCmdPO> queryWrapper = getQueryWrapper(1, dtuCmdDto);
        List<DtuCmdPO> dtuCmdPOS =dtuCmdDao.selectList(queryWrapper);
        List<DtuCmdVO>  listVo=new ArrayList<>();
        ListUtils.copyList(dtuCmdPOS,listVo, DtuCmdVO.class);
        return listVo;
    }

    /**
     * 查询条件组装
     *
     * @param sort
     * @param
     * @return
     */
    private LambdaQueryWrapper<DtuCmdPO> getQueryWrapper(Integer sort, DtuCmdDTO dtuCmdDto) {
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<DtuCmdPO> queryWrapper = new LambdaQueryWrapper<>();
        //针对回写命令。
        if(null!= dtuCmdDto && DtuCmdStatusEnum.rev.ordinal()==dtuCmdDto.getStatus() && StringUtil.isEmpty(dtuCmdDto.getDeviceSn()) &&  StringUtil.isEmpty(dtuCmdDto.getReqNo())){
            queryWrapper.isNotNull(DtuCmdPO::getSendCmd).eq(DtuCmdPO::getStatus, DtuCmdStatusEnum.send.ordinal())
            .eq(DtuCmdPO::getSendUrl, dtuCmdDto.getRevUrl());
        }
        //针对执行有请求单号和设备编号的命令的信息
        if(StringUtil.isNotEmpty(dtuCmdDto.getDeviceSn()) &&  StringUtil.isNotEmpty(dtuCmdDto.getReqNo())){
            queryWrapper.eq(DtuCmdPO::getDeviceSn, dtuCmdDto.getDeviceSn()).eq(DtuCmdPO::getReqNo, dtuCmdDto.getReqNo());
        }
        if (StringUtil.isNotEmpty(dtuCmdDto.getCmdNo())) {
            queryWrapper.eq(DtuCmdPO::getCmdNo, dtuCmdDto.getCmdNo());
        }
        //gid集合
        if (StringUtil.isNotEmpty(dtuCmdDto.getGid()) && null!= dtuCmdDto && DtuCmdStatusEnum.send.ordinal()!=dtuCmdDto.getStatus()) {
            queryWrapper.eq(DtuCmdPO::getGid, dtuCmdDto.getGid());
        }
        if (null!=dtuCmdDto.getMId()) {
            queryWrapper.eq(DtuCmdPO::getMId, dtuCmdDto.getMId());
        }
        if(StringUtil.isNotEmpty(dtuCmdDto.getSendUrl())){
            queryWrapper.eq(DtuCmdPO::getSendUrl, dtuCmdDto.getSendUrl());
        }
        if (StringUtil.isNotEmpty(dtuCmdDto.getRevUrl()) && DtuCmdStatusEnum.rev.ordinal()!=dtuCmdDto.getStatus()) {
            queryWrapper.eq(DtuCmdPO::getRevUrl, dtuCmdDto.getRevUrl());
        }

        //排序 0，降序，1，升序
        if (sort == 1) {
            queryWrapper.orderByAsc(DtuCmdPO::getCreateTime);
        } else {
            queryWrapper.orderByDesc(DtuCmdPO::getCreateTime);
        }
        return queryWrapper;
    }
}
