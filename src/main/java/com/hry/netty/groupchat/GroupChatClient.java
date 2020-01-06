package com.hry.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;


public class GroupChatClient {

    //属性
    private final String HOST ;
    private final int PORT ;

    public GroupChatClient(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public void run() throws InterruptedException {

        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            //得到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //加入相关的handler
                            //对入站的数据进行解码
                            pipeline.addLast("decoder", new StringDecoder());
                            //对出站的数据进行编码
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己的业务处理的handler
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });


            ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
            //得到channel
            Channel channel = channelFuture.channel();
            System.out.println("============" + channel.localAddress() + "===========");
            //客户端需要输入的信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String msg = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }


        } finally {

            eventExecutors.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GroupChatClient("127.0.0.1", 7000).run();
    }

}
