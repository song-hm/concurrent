package com.shm.design.patterns.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //构造FutureTask
        FutureTask<String> future = new FutureTask<>(new RealData("a"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        //执行FutureTask,在这里开启线程进行RealData的call（）方法执行
        executorService.submit(future);

        System.out.println("请求完毕");
        try {
            //代替其他业务逻辑处理过程
            Thread.sleep(2000);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }

        System.out.println("数据="+future.get());
    }
}
