package com.shm.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr){

        //记录最后一次交换的位置
        int lastExchangeIndex = 0;
        //无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = arr.length - 1;

        for (int i = arr.length - 1; i  > 0; i--){//控制进行比较的轮数
            //有序标记，每一轮的初始是true
            boolean sorted = true;
//            for (int j = 0; j < i; j++){//每轮进行比较的次数
            for (int j = 0; j < sortBorder; j++){//每轮进行比较的次数
                if (arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    //有元素交换，所以不是有序，标记变为false
                    sorted = false;
                    //把无序数列的边界更新为最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if(sorted) {
                break;
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void main(String[] args) {
        int[] arr = {6,9,2,1,7,4,3,8,0,5,7};
        bubbleSort(arr);
    }
}
