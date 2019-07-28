package com.shm.echo.internetNIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 准备好了在通知我：网络NIO
 * Java NIO是New IO的简称，是一种可以替代Java IO的一套新的IO机制
 * 用NIO构造多线程Echo服务器
 */
public class NIOEchoServer {
    private static Selector selector;
    private static ExecutorService tp = Executors.newCachedThreadPool();

    //统计在某一个Socket上花费的事件，time_stat的key为Socket，value为时间戳（记录处理开始时间）
    public static Map<Socket,Long> time_stat = new HashMap<Socket, Long>(10240);

    private void startServer() throws Exception{
        //通过工厂方法获得一个Selector对象的实例
        selector = SelectorProvider.provider().openSelector();
        //获得表示服务端的SocketChannel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置为非阻塞模式

        //端口绑定
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
//        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);

        //将ServerSocketChannel绑定到Selector上，并注册它感兴趣的事件为OP_ACCEPT，
        // 方法register()的返回值SelectionKey表示一对Selector和Channel的关系
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (;;){//无穷循环，等待和分发网络消息
            selector.select();//阻塞方法
            //获取准备好的SelectionKey
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            //得到迭代器
            Iterator<SelectionKey> i = readyKeys.iterator();
            long e = 0;
            //遍历集合
            while (i.hasNext()){
                SelectionKey sk = i.next();
                i.remove();//处理完该SelectionKey，将其从集合内删除

                if (sk.isAcceptable()){//是否在Acceptable状态
                    //进行客户端的接受
                    doAccept(sk);
                }else if (sk.isValid() && sk.isReadable()){//是否可读
                    //为统计系统处理每一个连接的时间，记录读取数据前的一个时间戳
                    if (!time_stat.containsKey(((SocketChannel)sk.channel()).socket()))
                        time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                    doRead(sk);//读取
                }else if (sk.isValid() && sk.isWritable()){//判断是否准备好进行写
                    doWrite(sk);//写入
                    e = System.currentTimeMillis();
                    Long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    //输出这个连接的耗时
                    System.out.println("spend:"+(e-b)+"ms");
                }
            }
        }
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1){
//                disconnect(sk);已废弃
                sk.cancel();
                return;
            }

            if (bb.remaining() == 0){
                //the buffer was completely written,remove it.
                outq.removeLast();
            }
        } catch (IOException e) {
            System.out.println("Failed to write new client");
            e.printStackTrace();
//            disconnect(sk);
            sk.cancel();
        }
        if (outq.size() == 0){
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(bb);
            if (len < 0){
//                disconnect(sk);
                sk.cancel();
                return;
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client");
            e.printStackTrace();
//            disconnect(sk);
            sk.cancel();
            bb.flip();
            tp.execute(new HandleMsg(sk,bb));
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {

            clientChannel = server.accept();//和客户端通信的通道
            clientChannel.configureBlocking(false);

            //register this channel for reading
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            //Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from"+ clientAddress.getHostAddress()+".");
        } catch (IOException e) {
            System.out.println("Failed to accept new client");
            e.printStackTrace();
        }
    }

    static class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            //将接收到的数据压入EchoClient的队列
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强迫selector立即返回
            selector.wakeup();
        }
    }

    public static void main(String[] args) throws Exception {
        new NIOEchoServer().startServer();

        while (true){
            Thread.sleep(1000);
        }
//        SelectionKey sk = null;
//        ByteBuffer bb = null;
//
//        ServerSocket echoServer = null;
//        Socket clientSocket = null;
//        try {
//            echoServer = new ServerSocket(8000);
//        } catch (IOException e) {
////            e.printStackTrace();
//            System.out.println(e);
//        }
//        while (true){
//            try {
//                clientSocket = echoServer.accept();
//                System.out.println(clientSocket.getRemoteSocketAddress() + "connect!");
//                tp.execute(new HandleMsg(sk,bb));
//            } catch (IOException e) {
////                e.printStackTrace();
//                System.out.println(e);
//            }
//        }
    }
}
