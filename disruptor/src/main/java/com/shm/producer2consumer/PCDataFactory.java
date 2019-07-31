package com.shm.producer2consumer;

import com.lmax.disruptor.EventFactory;

/**
 * 产生PCData对象的工厂类，在Disruptor框架初始化时，构造所有的缓冲区中的对象实例
 */
public class PCDataFactory implements EventFactory<PCData> {
    public PCData newInstance() {
        return new PCData();
    }
}
