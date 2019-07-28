package com.shm.echo.internetNIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * 用NIO构造多线程Echo客户端
 */
public class NIOEchoClient {
    private Selector selector;
    public void init(String ip,int port)throws IOException{
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip,port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException{
        while (true){
            if (!selector.isOpen())
                break;
            selector.select();
            Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey key = ite.next();
                ite.remove();
                //连接事件发生
                if (key.isConnectable()){
                    connect(key);
                }else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到的信息："+msg);
        channel.close();
        key.selector().close();
    }

    public void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //如果正在连接，则完成连接
        if (channel.isConnectionPending()){
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        NIOEchoClient nioEchoClient = new NIOEchoClient();
        nioEchoClient.init("127.0.0.1",8000);
        nioEchoClient.working();

        Thread.sleep(5000);

//        Socket client = null;
//        PrintWriter writer = null;
//        BufferedReader reader = null;
//        try {
//
//            client = new Socket();
//            client.connect(new InetSocketAddress("localhost",8000));
//            writer = new PrintWriter(client.getOutputStream(),true);
//            writer.println("hello!");
//            writer.flush();
//
//            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            System.out.println("from Server:"+ reader.readLine());
//        }catch (UnknownHostException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if (writer != null)
//                writer.close();
//            if (reader != null)
//                reader.close();
//            if (client != null)
//                client.close();
//        }
//
   }
}
