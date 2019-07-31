package com.shm.design.patterns.prducer2consumer;

/**
 * PCData对象作为生产者和消费者之间的共享数据模型
 */
public final class PCData {
    private final int intData;

    public PCData(int intData) {
        this.intData = intData;
    }

    public PCData(String d){
        intData = Integer.valueOf(d);
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "data=" + intData;
    }
}
