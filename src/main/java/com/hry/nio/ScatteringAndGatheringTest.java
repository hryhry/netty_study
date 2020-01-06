package com.hry.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/*
*  Scattering ：将数据写入到buffer时，可以采用buffer数组，依次写入 [分散]
*  Gathering ：从buffer读取数据时，可以采用buffer数组，依次读出
* */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {

        //使用 ServerSocketChannel 和SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        //绑定端口到Socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buff数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接（telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLen = 8;
        //循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLen){
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead= " + byteRead);
                //使用流打印，看当前的buffer
                Arrays.asList(byteBuffers).stream().map(buffer -> "position" + buffer.position() + ", limit=" + buffer.limit()).forEach(System.out::println);
            }

            //将所有buffer进行flip
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            //将数据读出，显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLen){
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }

            //将所有buffer进行复位操作
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());

            System.out.println("readbyteRead= " + byteRead + " byteWrite=" + byteWrite + " msgLen=" + msgLen);

        }



    }
}
