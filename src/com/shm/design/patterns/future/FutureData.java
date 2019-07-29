package com.shm.design.patterns.future;

/**
 * 他是真实数据的代理，封装了获取RealData的等待过程
 */
public class FutureData implements Data {
    protected RealData realData = null; //FutureData是RealData的包装
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if (isReady){
            return;
        }
        this.realData = realData;
        isReady = true;
        notify();  //RealData已经注入，通知getResult()方法
    }
    @Override
    public synchronized String getResult() {//等待RealData构造完成
        while (!isReady){
            try {
                wait();  //一直等待，直到RealData被注入
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
        return realData.result; //由RealData实现
    }
}
