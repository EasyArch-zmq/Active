package com.easyArch.net;

import com.easyArch.mapper.Time_InfoDao;
import com.easyArch.util.ControllerUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configuration
@ChannelHandler.Sharable
public class SocketHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOGGER= LoggerFactory.getLogger(SocketHandler.class);
    ControllerUtil util=new ControllerUtil();
    @Autowired
    Time_InfoDao timeInfoDao;
    //这里就是连数据要的dao要注入

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o) {
        LOGGER.info("收的数据"+o);
        String []strings=util.getInfo(o);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取日期
        String mytime=df.format(new Date());
        String boxid=strings[0];
        Integer yangan=new Integer(strings[1]);
        timeInfoDao.insertInfo(boxid,mytime,yangan);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info("客户端加入连接:"+ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channel.closeFuture();
        LOGGER.info("客户端断开连接："+ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            channel.closeFuture();
        }
    }
}
