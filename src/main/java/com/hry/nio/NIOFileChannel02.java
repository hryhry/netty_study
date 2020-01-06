package com.hry.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
*
* 将数据通过channel从本地加载到内存中
* */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {

        //创建文件的输入流
        FileInputStream fileInputStream = new FileInputStream("e:/file01.txt");

        //通过fileInputStream创建对应的fileChannel，实际类型是fileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将fileChannel通道内的数据放入到byteBuffer缓冲区
        fileChannel.read(byteBuffer);
        //byteBuffer进行filp()
        byteBuffer.flip();

        //将byteBuffer缓冲区的数据转换成String，并进行打印
        System.out.println(new String(byteBuffer.array()));

        //关闭流
        fileInputStream.close();
    }

}
