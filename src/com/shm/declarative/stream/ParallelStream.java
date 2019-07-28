package com.shm.declarative.stream;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ParallelStream {
public static void main(String[] args) {
    /**
     * 使用并行流过滤数据
     * 场景：统计1-1000000内质数的数量
     */
    //函数式编程：串行方式
    long count = IntStream.range(1, 1000000).filter(PrimeUtil::isPrime).count();
    System.out.println(count);
    //并行计算方式
    count = IntStream.range(1,1000000).parallel().filter(PrimeUtil::isPrime).count();
    System.out.println(count);

    /**
     * 从集合得到并行流
     * 场景：统计集合内所有学生的平均分
     */
    ArrayList<Student> ss = new ArrayList<Student>();
    //串行方式
    double ave = ss.stream().mapToInt(s -> s.getScore()).average().getAsDouble();
    //并行方式
    ave = ss.parallelStream().mapToInt(s -> s.getScore()).average().getAsDouble();
}
}
