package com.shm.algorithm.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行搜索
 */
public class PSearch {
    static int[] arr = {6,9,2,1,7,4,3,8,0,5,7};
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    //保存符合条件的元素在arr数组中的下标，默认为1，表示没有找到元素
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int beginPos,int endPos,int searchValue) {
        int i = 0;
        for (i = beginPos; i < endPos; i++) {
            if (result.get() >= 0) {
                return result.get();
            }

            if (arr[i] == searchValue) {
                //如果设置失败，则表示其他线程已经找到了
                if (!result.compareAndSet(-1, i)) {
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }
    public static class SearchTask implements Callable<Integer>{
        int begin, end, searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() throws Exception {
            int re = search(begin,end,searchValue);
            System.out.println(re);
            return re;
        }
    }

    public static int pSearch(int searchValue) throws InterruptedException, ExecutionException{
        int subArrSize = arr.length/Thread_Num + 1;
        List<Future<Integer>> re = new ArrayList<Future<Integer>>();
        for (int i=0; i<arr.length; i+=subArrSize){
            int end = i+subArrSize;
            if (end>=arr.length)
                end = arr.length;
            re.add(pool.submit(new SearchTask(i,end,searchValue)));
        }
        for (Future<Integer> fu:re){
            if (fu.get()>=0)
                return fu.get();
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        pSearch(7);
    }
}
