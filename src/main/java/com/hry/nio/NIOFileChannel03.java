package com.hry.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
* 文件的拷贝
* */

public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("e:/file01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("e://file02.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        while (true) {
            int read = fileInputStreamChannel.read(byteBuffer);
            if (read != -1){
                //将byteBuffer进行翻转，读数据变为取数据
                byteBuffer.flip();
                fileOutputStreamChannel.write(byteBuffer);
            } else {
                break;
            }
            //将byteBuffer进行复位
            byteBuffer.clear();
        }
        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
    }
}
