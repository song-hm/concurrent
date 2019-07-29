package com.shm.design.patterns.future;

import java.util.concurrent.Callable;

/**
 * 真实数据，即最终需要使用的数据模型
 */
//public class RealData implements Data{//简单实现
//    protected final String result;
//
//    public RealData(String para) {
//        //RealData的构造可能很慢，需要用户等待很久，这里使用sleep模拟
//        StringBuffer sb = new StringBuffer();
//        for (int i=0; i<10; i++){
//            sb.append(para);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
////                e.printStackTrace();
//            }
//        }
//        result = sb.toString();
//    }
//
//    @Override
//    public String getResult() {
//        return result;
//    }
//}

public class RealData implements Callable<String>{//jdk内置的future模式
    protected String result;

    public RealData(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(result);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
