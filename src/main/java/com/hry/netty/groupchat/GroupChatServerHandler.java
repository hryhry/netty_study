package com.hry.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组， 管理所有的channel
    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //使用一个HashMap管理channel
    //private static Map<String, Channel> channelMap = new HashMap<>();


    //handlerAdded表示连接建立，一旦建立，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush()或遍历channelGroup中所有的channel遍历，并发送消息
        // 将该客户加入连天的信息推送给其他在线的客户端
        channelGroup.writeAndFlush("【客户端】： " + ctx.channel().remoteAddress() + "加入聊天     " + sdf.format(System.currentTimeMillis()));
        //将当前channel加入到channelGroup中
        channelGroup.add(ctx.channel());

        //channelMap.put(ctx.channel().id().toString(), ctx.channel());


    }

    //handlerRemoved()表示连接断开，一旦断开，被执行
    // channelGroup中对应的channel也会被自动移除
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        // writeAndFlush()或遍历channelGroup中所有的channel遍历，并发送消息
        // 将该客户加入连天的信息推送给其他在线的客户端
        channelGroup.writeAndFlush("【客户端】： " + ctx.channel().remoteAddress() + "离开了     " + sdf.format(System.currentTimeMillis()));
        System.out.println("当前在线人数：" + channelGroup.size());

    }

    //表示channel处于活动的状态，提示 xx上线   给服务器端看的
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示channel处于非活动的状态，提示 xx离线线   给服务器端看的
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        //获取到当前channel
        Channel channel = ctx.channel();
        //这时我们便利channelGruop,根据不同的情况，回应不同的消息
        channelGroup.forEach(ch -> {

            if (channel != ch){  //不是当前的channel ,直接转发
                ch.writeAndFlush("【客户-" + channel.remoteAddress() + "】: " + msg + "\n");
            } else { //回显自己发送的消息
                ch.writeAndFlush("【自己】：" + msg + "\n");
            }

        }) ;

    }


    //发生异常时
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
