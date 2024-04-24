package com.app.device.handler.netty.impl;

import com.app.device.domain.Wwj.DtuCmdDTO;
import com.app.device.domain.loginVo.User;
import com.app.device.handler.UserInfoContext;
import com.app.device.services.IDeviceService;
import com.app.device.services.wwj.IHardwareWwjService;
import com.app.device.type.DeviceTypeEnum;
import com.app.device.type.DtuCmdStatusEnum;
import com.app.device.utils.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//物联网 开启检测端口 并写入数据库
@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private final static Logger log = LoggerFactory.getLogger(ServerHandler.class);
    //此处注入数据源操作sql   执行插入设备上传的数据

    @Autowired
    IDeviceService deviceService;
    @Autowired
    IHardwareWwjService hardwareWwjService;
    //  将当前客户端连接 存入map   实现控制设备下发 参数
    public static Map<String, ChannelHandlerContext> map = new HashMap<String, ChannelHandlerContext>();

    /**
     * 获取数据
     *
     * @param ctx 上下文
     * @param msg 获取的数据
     * @throws UnsupportedEncodingException
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        //msg为接收到的客户端传递的数据   个人这边直接传的json 数据
        ByteBuf readMessage = (ByteBuf) msg;
        log.info("channelRead--接收到的数据" + readMessage.toString(CharsetUtil.UTF_8));
        if(!StringUtil.isJSON(readMessage.toString(CharsetUtil.UTF_8))){
            handleCallBackOrder(ctx,readMessage.toString(CharsetUtil.UTF_8));
            return;
        }
        JSONObject json = JSONObject.fromObject(readMessage.toString(CharsetUtil.UTF_8));
        //解析客户端json 数据

        //获取客户端的请求地址  取到的值为客户端的 ip+端口号
        String url = ctx.channel().remoteAddress().toString();//设备请求地址（个人将设备的请求地址当作 map 的key）

        if (map.get(url) != null) {//如果不为空就不存

        } else {//否则就将当前的设备ip+端口存进map  当做下发设备的标识的key
            map.put(url, ctx);
        }
        int users = 0;
        //设备请求的 服务器端的地址   用作监听设备请求的那个端口
        String servicePort = ctx.channel().localAddress().toString();
        //判断端口如果客户端请求的端口号为9898   就是写入第一张表   这样可以实现 设备传递数据参数不一致
        log.info("向：" + servicePort.substring(servicePort.length() - 4, servicePort.length()) + " 端口写入数据");
        if (servicePort.substring(servicePort.length() - 4, servicePort.length()).equals("9898")) {

                addDeviceData(json, url, servicePort, readMessage);

        } else {
                addDeviceData(json, url, servicePort, readMessage);
        }
        String rmsg;
        if (users > 0) {
            rmsg = "11 02 00 C4 00 16 ";//返回成功的信息
        } else {
            rmsg = "0";//返回失败的信息
        }
        ByteBuf message = Unpooled.copiedBuffer(rmsg.getBytes());//处理返回的信息
        //ctx.write(in2);//返回信息
        ctx.writeAndFlush(message);//返回信息
        //刷新缓存区
        ctx.flush();
    }

    /**
     * 处理反馈命令信息
     * @param
     */
    private void handleCallBackOrder(ChannelHandlerContext ctx,String readMessage){
          DtuCmdDTO dtuCmdDto=new DtuCmdDTO();
          dtuCmdDto.setRevCmd(readMessage);
          dtuCmdDto.setRevUrl(ctx.channel().remoteAddress().toString());
          dtuCmdDto.setRevTime(new Date());
          dtuCmdDto.setLastTime(new Date());
          User context = UserInfoContext.getUser();
          if(null!=context) {
              dtuCmdDto.setUserId(context.getUserId());
              dtuCmdDto.setMId(context.getMId());
          }
          dtuCmdDto.setStatus(DtuCmdStatusEnum.rev.ordinal());
          String msg= hardwareWwjService.saveDtuCmd(dtuCmdDto);
          if(StringUtil.isNotEmpty(msg)){
              log.error("handleCallBackOrder--处理错误==="+msg);
          }
    }
    /**
     * 长连接写入数据库
     * @param json
     * @param url
     * @param servicePort
     * @param readMessage
     */
    private void addDeviceData(JSONObject json, String url, String servicePort, ByteBuf readMessage) {
        try {
            Map<String, String> dataMap = new HashMap<>();
            //设备请求地址  存入数据库  下方controller层 通过设备id查询此地址   取到map种存入的 ChannelHandlerContext 实现数据下发
            dataMap.put("mac", json.get("iccid").toString());
            dataMap.put("deviceName", DeviceTypeEnum.wwj.toString());
            dataMap.put("gatewaySn", url);
            dataMap.put("port", servicePort);
            dataMap.put("ip", url);
            dataMap.put("stauts", "1");
            dataMap.put("upJson", json.toString());//获取的json数据
            dataMap.put("deviceSn", json.get("imei").toString());//设备数据1
            dataMap.put("csq", json.get("csq").toString());
            dataMap.put("fver", json.get("fver").toString());
            dataMap.put("remark", readMessage.toString(CharsetUtil.UTF_8));//设备请求时原生数据
            deviceService.insertUpdate(dataMap, DeviceTypeEnum.wwj.toString(), 0);
        } catch (Exception e) {
           log.error("addSqlData---写入数据出错"+e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}