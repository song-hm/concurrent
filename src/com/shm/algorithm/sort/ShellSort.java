package com.shm.algorithm.sort;

import java.util.Arrays;

/**
 * 改进的插入排序：
 * 希尔排序
 * @author Administrator
 *
 */
public class ShellSort {
	
	public static void shellSort(int[] arr) {
		System.out.println(Arrays.toString(arr));
		//计算出最大的h值
		int h = 1;
		while (h <= arr.length / 3) {
			h = h * 3 + 1;			
		}
		
		while (h > 0) {
			System.out.println("h="+h);
			for (int i = h; i < arr.length; i++) {
				if (arr[i] < arr[i - h]) {
					int tmp = arr[i];
					int j = i - h;
					while (j >= 0 && arr[j] > tmp) {
						arr[j + h] = arr[j];
						j -= h;
					}
					arr[j + h] = tmp;
				}
			}
			//计算下一个h值
			h = (h - 1) / 3;
			System.out.println(Arrays.toString(arr));
		}
	}
	
	public static void main(String[] args) {
		int arr[] = {6,2,8};
		shellSort(arr);
	}

}
