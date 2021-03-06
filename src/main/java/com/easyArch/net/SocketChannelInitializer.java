package com.easyArch.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private SocketHandler socketHandler;
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //防止tcp拆、粘包使用换行符来防止
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));

        channel.pipeline().addLast(new HexDecode());
        channel.pipeline().addLast(socketHandler);

    }
}
