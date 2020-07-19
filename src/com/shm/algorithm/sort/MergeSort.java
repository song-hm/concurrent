package com.shm.algorithm.sort;

import java.util.Arrays;

public class MergeSort {
    static int[] aux;

    public static void main(String[] args){
        int[] array = new int[]{5, 3, 8, 6, 9, 2, 1, 7};
//        MergeSort ms = new MergeSort();
        mSort(array);
        System.out.println(Arrays.toString(array));
    }


    public static void mSort(int[] num){
        aux = new int[num.length];
        sort(num,0,num.length-1);
    }

    public static void sort(int[] num,int l,int h){
        if (h<=l){
            return;
        }
        int mid = l+(h-l)/2;
        sort(num,l,mid);
        sort(num,mid+1,h);
        merge(num,l,mid,h);
    }

    public static void merge(int[] nums,int l,int m,int h){
        int i,j,k;
        for (int n = l; n <=h; n++) {
            aux[n] = nums[n];
        }

        for (i = l,j=m+1; l<=m && j<=h ; i++) {
            if (aux[l] <aux[j]){
                nums[i] = aux[l++];
            }else{
                nums[i] = aux[j++];
            }
        }

        if (l<=m){
            for (k = 0; k <= m-l; k++) {
                nums[i+k] = aux[l+k];
            }
        }

        if (j<=h){
            for (k = 0; k <= h-j; k++) {
                nums[i+k] = aux[j+k];
            }
        }
    }
}
