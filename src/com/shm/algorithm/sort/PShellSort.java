package com.shm.algorithm.sort;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 并行希尔排序
 * @author Administrator
 *
 */
public class PShellSort {
	static ExecutorService pool = Executors.newCachedThreadPool();
	public static class ShellSortTask implements Runnable{
		int i = 0;
		int h = 0;
		CountDownLatch l;
		int[] arr;
		
		public ShellSortTask(int i, int h, CountDownLatch l, int[] arr) {
			super();
			this.i = i;
			this.h = h;
			this.l = l;
			this.arr = arr;
		}

		public void run() {
			if (arr[i] < arr[i - h]) {
				int tmp = arr[i];
				int j = i - h;
				while (j >= 0 && arr[j] > tmp) {
					arr[j + h] = arr[j];
					j -= h;
				}
				arr[j + h] = tmp;	
			}
			System.out.println(Thread.currentThread().getName());
			System.out.println(Arrays.toString(arr));
			l.countDown();
		}
		
	}
	
	public static void pShellSort(int[] arr) throws InterruptedException{
//		ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>(10));
		//计算最大的h值
		int h = 1;
		CountDownLatch latch = null;
		while(h <= arr.length / 3) {
			h = h * 3 + 1;
		}
		
		while (h > 0) {
			System.out.println("h=" + h);
			if(h >= 4)
				latch = new CountDownLatch(arr.length - h);
			for (int i = h; i < arr.length; i++) {
				//控制线程数
				if (h >= 4) {
					pool.execute(new ShellSortTask(i, h, latch, arr));
				}else {
					if(arr[i] < arr[i - h]) {
						int tmp = arr[i];
						int j = i - h;
						while(j >= 0 && arr[j] > tmp) {
							arr[j + h] = arr[j];
							j -= h;
						}
						arr[j + h] = tmp;
					}
					System.out.println(Arrays.toString(arr));
				}
			}
			//等待线程排序完成，进入下一次排序
			latch.await();
			//计算下一个h值
			h = (h - 1) / 3;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		int arr[] = {6,9,2,1,7,4,3,8,0,5,7};
		pShellSort(arr);
	}

}
