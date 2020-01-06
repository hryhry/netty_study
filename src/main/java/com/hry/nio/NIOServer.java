package com.hry.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/*
*   NIO非阻塞快速入门
* */
public class NIOServer {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector
        Selector selector = Selector.open();

        //绑定一个端口号
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //将serverSocketChannel先注册到selector上，关心的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectionKey数量=" + selector.keys().size());

        //循环等待客户端连接
        while (true) {

            //这里等待一秒，如果还是没有事件发生，就进行其他操作
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1s，无连接！");
                continue;
            }
            //如果返回的不为0，就获取相关的SelectionKey集合
            // 1. 如果返回》0，便是已经获取到关注的事件
            // 2. selector.selectedKeys(); 返回关注事件的集合
            //  通过SelectionKey可以反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历Set<SelectionKey>
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()){

                //获取到selectionKey
                SelectionKey key = selectionKeyIterator.next();
                //根据key, 对应的通道发生的事件做相应的处理
                if (key.isAcceptable()){ //有新的客户端连接
                    //给该客户端生成SocketChannel  ,这里的accept()是阻塞的，但是现在时已经有客户端连接了，所以这里就会马上执行，不会产生阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功");
                    //将刚创建的socketChannel注册到Selector，关注事件为OP_READ，
                    // 同时给channel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("注册后的selectionKey数量=" + selector.keys().size());

                }
                if (key.isReadable()) { //发生OP_READ
                    //通过key获取到对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端: " + new String(buffer.array()));
                    //将buffer进行清空
                    buffer.clear();
                }

                //手动从当前的集合中移除SelectionKey,防止重复操作
                selectionKeyIterator.remove();

            }

        }

    }

}
