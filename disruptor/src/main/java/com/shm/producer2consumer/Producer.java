package com.shm.producer2consumer;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Producer {
    private final RingBuffer<PCData> ringBuffer;//RingBuffer即环形缓冲区

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * pushData()方法将产生的数据推入缓冲区
     *
     * @param bb ByteBuffer对象用来包装任何数据类型
     *           这里用来存储long整形
     */
    public void pushData(ByteBuffer bb){
        long sequence = ringBuffer.next();//得到下一个可用的序列号
        try {
            PCData event = ringBuffer.get(sequence);//通过序列号，取下一个空闲可用的PCData对象
            event.setValue(bb.getLong(0));//将PCData对象的数据设为期望值，这个值最终会传递给消费者
        }finally {
            ringBuffer.publish(sequence);//数据发布，只有发布的数据才会真正被消费者看见
        }
    }
}
