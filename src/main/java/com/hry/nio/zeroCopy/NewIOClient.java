package com.hry.nio.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String filename = "";

        FileChannel fileChannel = new FileInputStream(filename).getChannel();




        fileChannel.transferTo(0, fileChannel.size(), socketChannel);


        fileChannel.close();
        //准备发送



    }
}
