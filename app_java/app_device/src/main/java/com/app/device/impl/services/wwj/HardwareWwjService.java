package com.app.device.impl.services.wwj;

import com.app.device.handler.wwj.ClientSendSocket;
import com.app.device.handler.wwj.Connect;
import com.app.device.handler.wwj.ServerThread;
import com.app.device.handler.wwj.SocketClient;
import com.app.device.services.wwj.IHardwareWwjService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

@Service
public class HardwareWwjService implements IHardwareWwjService {
    private final static Logger log = LoggerFactory.getLogger(HardwareWwjService.class);
    @Value("${server.socket.port}")
    private Integer port;

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
}
