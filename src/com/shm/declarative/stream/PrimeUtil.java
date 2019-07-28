package com.shm.declarative.stream;

/**
 * 基本判断思路
 * 在一般领域，对正整数n，如果用2到sqrt(n)-n的开方
 * 之间的所有整数去除，均无法整除，则n为质数。
 * 质数大于等于2 不能被它本身和1以外的数整除
 * 判断一个数是否是质数的函数
 */
public class PrimeUtil {
    public static boolean isPrime(int number){
        int temp = number;
        if (temp < 2){
            return false;
        }
        for (int i = 2; i <= Math.sqrt(temp); i++){
            if (temp%i == 0){
                return false;
            }
        }
        return true;
    }
}
