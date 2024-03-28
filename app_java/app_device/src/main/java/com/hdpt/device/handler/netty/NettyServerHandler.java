package com.hdpt.device.handler.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: ReWinD00
 * @date: 2021-07-05 10:43
 */
@Component
@Scope("prototype")
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private int idleCounter = 0;

    /**
     * 业务数据处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            idleCounter = 0;
            ByteBuf in = (ByteBuf) msg;
            int readableBytes = in.readableBytes();
            byte[] bytes = new byte[readableBytes];
            in.readBytes(bytes);
            String msgStr = new String(bytes, StandardCharsets.UTF_8);
            log.info("服务端接受的消息 : " + msgStr);

            //System.out.print(in.toString(CharsetUtil.UTF_8));
//            JSONObject jsonObject = JSON.parseObject(msgStr);
//            if (jsonObject.getString("data").equals("up")) {
//                log.info("设备{}上线了", jsonObject.getInteger("id"));
//                ChannelHandlerContextHolder.updateMap(jsonObject.getInteger("id"), ctx);
//            } else {
//                log.info("设备上送信息{}", jsonObject.getString("data"));
//            }
            String message = in.toString(Charset.forName("utf-8"));
            if(!message.contains("heartbeat")){
                ByteBuf out = getByteBuf(ctx);
                ctx.channel().writeAndFlush(out);
            }
            
            if ("shutdown".equals(msgStr)) {
                ctx.writeAndFlush(Unpooled.copiedBuffer("shutdown\r\n".getBytes()));
            } else {
                ctx.writeAndFlush(Unpooled.copiedBuffer("success\r\n".getBytes()));
            }
//            Thread.sleep(100);

        } finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }

    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        log.info("转发给客户端信息："+ctx);
        ChannelMap.getChannelMap().put("ttt", ctx);
        log.info("ChannelMap.getChannelMap==="+ChannelMap.getChannelMap()+"---msgId:");
        byte[] bytes = "我是发送给客户端的数据：请重启冰箱!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }

    
    /**
     * 从客户端收到新的数据、读取完成---调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("从客户端收到新的数据读取完成********");
        if (ctx != null) {
            ctx.flush();
        }
    }

    /**
     * 客户端与服务端建立连接--执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().read();
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        //此处不能使用ctx.close()，否则客户端始终无法与服务端建立连接
        log.info("客户端与服务端建立连接:{}", clientIp);
    }


    /**
     * 客户端与服务端断连-调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        //断开连接时，服务端关闭连接，避免造成资源浪费
        ctx.close();
        log.info("客户端与服务端断连:{}", clientIp);
    }


    /**
     * 当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx != null) {
            ChannelHandlerContextHolder.removeByCtx(ctx);
            //抛出异常，断开与客户端的连接
            ctx.close();
            log.error("连接异常，服务端主动断开连接{}", cause.getMessage(), cause);
        }

    }

    /**
     * 服务端read超时-调用
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        String clientIp = this.getClientIp(ctx);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state().equals(IdleState.WRITER_IDLE)) {
                log.warn("客户端写超时:{}", clientIp);
                idleCounter++;
                if (idleCounter > 3) {
                    log.warn("客户端写超时超过3次，断开");
                    ctx.disconnect();
                }
            }
        }
    }


    private String getClientIp(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        return clientIP;
    }
}