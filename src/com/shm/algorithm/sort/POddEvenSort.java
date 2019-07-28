package com.shm.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 奇偶交换排序（并行）
 */
public class POddEvenSort {
    static int exchFlag = 1;
    static ExecutorService pool = Executors.newCachedThreadPool();
    static synchronized void setExchFlag(int v){
        exchFlag = v;
    }
    static synchronized int getExchFlag(){
        return exchFlag;
    }

    public static class OddEvenSortTask implements Runnable{

        int i;
        CountDownLatch latch;
        int[] arr;
        public OddEvenSortTask(int i, CountDownLatch latch, int[] arr){
            this.i = i;
            this.latch = latch;
            this.arr = arr;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i+1]){
                int temp = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = temp;
                setExchFlag(1);
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println(Arrays.toString(arr));
            latch.countDown();

        }
    }

    public static void pOddEvevSort(int[] arr) throws InterruptedException{
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(10));
        int start = 0;
        while (getExchFlag() == 1 || start == 1){
            setExchFlag(0);
            //偶数的数组长度，当start为1时，只有len/2 - 1个线程
            CountDownLatch latch = new CountDownLatch(arr.length/2 - (arr.length%2==0?start:0));
            for (int i = start; i < arr.length-1; i += 2){
                pool.submit(new OddEvenSortTask(i, latch, arr));
            }
            //等待所有线程结束
            latch.await();
            if (start == 0)
                start = 1;
            else
                start = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] arr = {6,9,2,1,7,4,3,8,0,5,7};
        pOddEvevSort(arr);
    }
}
