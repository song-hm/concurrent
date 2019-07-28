package com.shm.echo.internetNIO;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class EchoClient {
    private LinkedList<ByteBuffer> outq;
    EchoClient(){
        outq = new LinkedList<ByteBuffer>();
    }
    public LinkedList<ByteBuffer> getOutputQueue(){
        return outq;
    }
    public void enqueue(ByteBuffer bb){
        outq.addFirst(bb);
    }
}
