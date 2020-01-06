package com.hry.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/*
*将数据通过channel从缓存写到本地中
* */
public class NIOFileChannel01 {

    public static void main(String[] args) throws IOException {
        String str = "hello,尚硅谷";

        FileOutputStream fileOutputStream = new FileOutputStream("e:/file01.txt");

        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将数据传入到byteBuffer中
        byteBuffer.put(str.getBytes());

        //对byteBuffer进行filp()
        byteBuffer.flip();
        //将byteBuffer中的数据写入到fileChannel中
        fileChannel.write(byteBuffer);

        //关闭流
        fileOutputStream.close();


    }
}
