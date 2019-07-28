package com.shm.algorithm.sort;

import java.util.Arrays;

/**
 * 分离数据相关性：
 * 奇偶交换排序（串行）
 * 奇交换和偶交换需成对出现，才能保证比较和交换涉及数组中的每一个元素
 */
public class OddEvenSort {
    public static void oddEvenSort(int[] arr){
        //exchFlag 记录当前迭代是否发生数据交换
        //start 表示是奇交换还是偶交换 初始时为0 表示偶交换
        int exchFlag = 1,start = 0;
        while(exchFlag == 1 || start == 1){//若上一次比较发生数据交换，或当前正进行的是奇交换，循环就不会停止。
            exchFlag = 0;
            for (int i = start; i < arr.length - 1; i += 2){
                if (arr[i] > arr[i+1]){
                    int temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    exchFlag = 1;
                }
            }
            System.out.println(Arrays.toString(arr));
            // 每次迭代结束，切换start的状态
            if (start == 0){
                start = 1;
            }else {
                start = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {6,9,2,1,7,4,3,8,0,5,7};
        oddEvenSort(arr);
    }
}
