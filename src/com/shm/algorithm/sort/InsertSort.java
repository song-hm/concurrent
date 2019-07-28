package com.shm.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 * @author Administrator
 *
 */
public class InsertSort {
	public static void insertSort(int[] arr) {		
		int j, i, key;
		for (i = 1; i < arr.length; i++) {
			// key 为要准备插入的元素
			key = arr[i];
			j = i - 1;
			while(j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j--;
			}
			// 找到合适的位置插入key
			arr[j + 1] = key;
			System.out.println(Arrays.toString(arr));
		}
	}
	
	public static void main(String[] args) {
		int arr[] = {6,9,2,1,7,4,3,8,0,5,7};
		insertSort(arr);
	}

}
