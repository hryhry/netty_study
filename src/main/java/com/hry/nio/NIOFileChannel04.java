package com.hry.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {

        //创建相关的流
        FileInputStream fileInputStream = new FileInputStream("e:/1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("e:/1_1.jpg");

        //获取各个流对应的channnel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();

        //使用transferFrom完成拷贝
        destChannel.transferFrom(sourceChannel,0, sourceChannel.size());
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }

}
