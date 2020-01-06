package com.hry.nio;

import java.nio.IntBuffer;

public class nio {
    /*
    * 一个Selector对应一个线程
    * 一个线程对应多个channel
    * 一个channel对应一个buffer
    *
    *
    * */
    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity()-1; i++){
            intBuffer.put(i*2);
        }

        //将buff转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }





    }




}
