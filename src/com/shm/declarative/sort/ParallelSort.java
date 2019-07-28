package com.shm.declarative.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 并行排序
 */
public class ParallelSort {
    public static void main(String[] args) {
        int[] arr = new int[10];

        /**
         * 给数组中每一个元素都附上一个随机值
         */
        Random r = new Random();
        //串行
        Arrays.setAll(arr,(i) -> r.nextInt());
        System.out.println(Arrays.toString(arr));

        //串行排序
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        //并行
        Arrays.parallelSetAll(arr, (i) -> r.nextInt());
        System.out.println(Arrays.toString(arr));

        //并行排序
        Arrays.parallelSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
