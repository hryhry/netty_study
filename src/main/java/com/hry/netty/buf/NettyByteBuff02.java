package com.hry.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuff02 {

    public static void main(String[] args) {

        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", Charset.forName("utf-8"));

        //使用相关的方法
        if (byteBuf.hasArray()) {

            byte[] array = byteBuf.array();
            //将array转换成字符串，并指定编码方式
            System.out.println(new String(array, Charset.forName("utf-8")));

            System.out.println("byteBuf= " + byteBuf);
            //偏移量
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());


        }


    }
}
