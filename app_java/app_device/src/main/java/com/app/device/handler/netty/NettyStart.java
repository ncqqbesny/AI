package com.app.device.handler.netty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import com.app.device.handler.netty.impl.ServerHandler;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
//物联网 开启检测 并写入数据库
@Component
public class NettyStart {
    @Resource
    private ServerHandler serverHandler;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();

    /**
     * 启动netty服务
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap b=new ServerBootstrap();
        b.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childHandler(new ChannelInitializer<SocketChannel>()  {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });
        ChannelFuture future = b.bind(9898).sync();//开启需要监听 的端口
        ChannelFuture future1 = b.bind(9899).sync();//开启需要监听 的端口 多开端口
        if (future.isSuccess()) {
            System.out.println("启动 9898 成功");
        }
        if (future1.isSuccess()) {
            System.out.println("启动 9899 成功");
        }

    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workGroup.shutdownGracefully().syncUninterruptibly();
        System.out.println("关闭 Netty 成功");
    }
}
