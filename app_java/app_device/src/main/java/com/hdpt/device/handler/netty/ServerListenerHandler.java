package com.hdpt.device.handler.netty;

import com.hdpt.device.impl.services.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ServerListenerHandler extends SimpleChannelInboundHandler<Message> {
    private final static Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    /**
     * 设备接入连接时处理
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ChannelMap.getChannelMap().put("ttt", ctx);
        log.info("ChannelMap.getChannelMap==="+ChannelMap.getChannelMap()+"---msgId:");
        log.info("有新的连接：[{}]", ctx.channel().id().asLongText()
        );
    }

    /**
     * 数据处理
     *
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        // 获取消息实例中的消息体
        String content = msg.getContent();
        // 对不同消息类型进行处理
        MessageEnum type = MessageEnum.getStructureEnum(msg);
        switch (type) {
            case CONNECT:
                // 将通道加入ChannelMap
                ChannelMap.getChannelMap().put(msg.getId(), ctx);
                log.info("ChannelMap.getChannelMap==="+ChannelMap.getChannelMap()+"---msgId:"+msg.getId());
                // 将客户端ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
                AttributeKey<String> key = AttributeKey.valueOf("id");
                ctx.channel().attr(key).setIfAbsent(msg.getId());

                // TODO 心跳消息处理
            case STATE:
                // TODO 设备状态
            default:
                System.out.println(type.content + "消息内容" + content);
        }
    }

    /**
     * 设备下线处理
     *
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.info("设备下线了:{}", ctx.channel().id().asLongText());
        // map中移除channel
        removeId(ctx);
    }

    /**
     * 设备连接异常处理
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常
        log.info("异常：{}", cause.getMessage());
        // map中移除channel
        removeId(ctx);
        // 关闭连接
        ctx.close();
    }

    private void removeId(ChannelHandlerContext ctx) {
        AttributeKey<String> key = AttributeKey.valueOf("id");
        // 获取channel中id
        String id = ctx.channel().attr(key).get();
        // map移除channel
        ChannelMap.getChannelMap().remove(id);
    }
}

