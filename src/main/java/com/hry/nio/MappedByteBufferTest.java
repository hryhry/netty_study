package com.hry.nio;

/*
* MappedByteBuffer可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
* */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("e:/file01.txt","rw");
        //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();

        /*
        *参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
        *参数2：0 ：可以直接读取的起始位置
        *参数3：5 ：映射到内存的大小，即可以将1.txt文件的多少个字节映射到内存
        *可以直接修改的范围就是 0-5
        * */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');

        randomAccessFile.close();
    }
}
