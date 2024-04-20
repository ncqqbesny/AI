package com.app.device.handler.netty;

import com.app.device.utils.SpringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author: yzg
 * @date: 2021-07-05 10:42
 */
@Component
@Slf4j
public class NettyServer extends Thread {

    public static String MsgCode = "GBK";

    @Value("${server.netty.port}")
    public Integer port;



    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workGroup = null;
        ServerBootstrap serverBootstrap = null;
        ChannelFuture future = null;
        try {
            //初始化线程组
            bossGroup = new NioEventLoopGroup();
            workGroup = new NioEventLoopGroup();
            //初始化服务端配置
            serverBootstrap = new ServerBootstrap();
            //绑定线程组
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(SpringUtils.getBean(NettyServerHandler.class))
                            .addLast(SpringUtils.getBean(ServerListenerHandler.class));

                        }
                    })
                    //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //将小的数据包包装成更大的帧进行传送，提高网络的负载
                    .childOption(ChannelOption.TCP_NODELAY, true);
            future = serverBootstrap.bind(new InetSocketAddress(port)).sync();
            log.info(" *************TCP服务端启动成功 Port：{}*********** ", port);
        } catch (Exception e) {
            log.error("TCP服务端启动异常", e);
        } finally {
            if (future != null) {
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    log.error("channel关闭异常：", e);
                }
            }
            if (bossGroup != null) {
                //线程组资源回收
                bossGroup.shutdownGracefully();
            }

            if (workGroup != null) {
                //线程组资源回收
                workGroup.shutdownGracefully();
            }


        }
    }

}