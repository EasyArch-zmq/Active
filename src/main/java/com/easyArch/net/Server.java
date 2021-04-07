package com.easyArch.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    @Autowired
    private SocketChannelInitializer socketChannelInitializer;
    private Thread thread;

    public void run(int port) {
        LOGGER.info("init server 服务器,端口号为:"+port);
        thread = new Thread(() -> {
            try {
                NioEventLoopGroup boss = new NioEventLoopGroup();
                NioEventLoopGroup work = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, work)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .childOption(ChannelOption.TCP_NODELAY, true)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .childHandler(socketChannelInitializer);

                ChannelFuture future = bootstrap.bind(port).sync();
                LOGGER.info("服务器启动");
                future.channel().closeFuture().sync();
                LOGGER.info("服务器关闭");
            } catch (InterruptedException e) {
                LOGGER.error("netty服务器异常：{}", e.getMessage());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    public void stop() {
        if (null != thread && thread.isAlive()){
            thread.interrupt();
        }
        LOGGER.info(">>>>>>>>>>> WebServer destroy success.");
    }

}
