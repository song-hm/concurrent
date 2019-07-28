package com.shm.chatRoom.UDP;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *UDP：单向通信之客户端
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //基本数据类型的传递
        long n = 2000L;

        //对象的传递：创建要发送的对象
        Person person = new Person(18, "宙斯");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //基本数据类型的传递
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(n);

        //对象的传递
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject(person);

        //获取字节数组流中的字节数组（我们要发送的数据）
        byte[] b = bos.toByteArray();
        //必须告诉数据报包要发到哪台计算机的哪个端口，发送的数据以及数据的长度
        DatagramPacket dp = new DatagramPacket(b,b.length,new 
                                             InetSocketAddress("localhost",8999));
        //创建数据报套接字：指定发送信息的端口
        DatagramSocket ds = new DatagramSocket(9000);
        //发送数据报包
        ds.send(dp);
        //关闭资源
        dos.close();
//        oos.close();
        bos.close();
        ds.close();
    }
}  