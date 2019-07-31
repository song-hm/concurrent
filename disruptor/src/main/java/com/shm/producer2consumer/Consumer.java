package com.shm.producer2consumer;

import com.lmax.disruptor.WorkHandler;

/**
 * 消费者实现为来自Disruptor框架WorkHandler接口
 */
public class Consumer implements WorkHandler<PCData> {

    public void onEvent(PCData event) throws Exception {//onEvent()方法为框架的回调方法，数据的读取已经由框架进行封装了
        System.out.println(Thread.currentThread().getId()+":event:--"+event.getValue()*event.getValue()+"--");
    }
}
