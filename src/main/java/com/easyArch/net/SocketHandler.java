package com.easyArch.net;

import com.easyArch.mapper.Time_InfoDao;
import com.easyArch.util.ControllerUtil;
import com.easyArch.util.HexUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Configuration
@ChannelHandler.Sharable
public class SocketHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER= LoggerFactory.getLogger(SocketHandler.class);
    ControllerUtil util=new ControllerUtil();
    @Autowired
    Time_InfoDao timeInfoDao;
    //这里就是连数据要的dao要注入


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
//
//        LOGGER.info("传入的数据是："+ControllerUtil.bytesToHexString(msg));
//        System.out.println(ControllerUtil.bytesToHexString(msg));
//
//    }

        @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o) {
        //这里就是要接的字符串o 你要自己解析split()一下
        String substring = o.substring(0, 12);
        LOGGER.info("mac"+substring);
        String str = o.substring(12);
        byte[] bytes = HexUtil.hexStringToBytes(str);
        String s = new String(bytes, StandardCharsets.UTF_8);
        LOGGER.info("收的数据"+substring+s);

        LOGGER.info("收的数据:"+o);
        String []strings=util.getInfo(o);
        String[] split = o.split("\\(");
        byte[] bytess = HexUtils.fromHexString(split[0]);
        LOGGER.info("解析的数据："+ Arrays.toString(bytess) +"\t"+split[1]);

        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取日期
//        String mytime=df.format(new Date());
//        String boxid=strings[0];
//        Integer yangan=new Integer(strings[1]);
//        timeInfoDao.insertInfo(boxid,mytime,yangan);
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
