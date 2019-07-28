package com.shm.declarative.lambda;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 *Java8函数式编程
 */
public class HelloFunction {
    static int[] arr={1,2,3,4,5,6,7,8,9,10};

    public static void main(String[] args) {
        //1.传统做法
        for (int i : arr) {
//            System.out.println(i);
        }

        //2.使用java8中的流
        Arrays.stream(arr).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
//                System.out.println(value);
            }
        });

        //3.forEach()函数的参数可以从上下文中推导出来，就交给编译器去做
        Arrays.stream(arr).forEach((final int x) -> {
//            System.out.println(x);
        });

        //4.参数的类型也可以推导出来
        Arrays.stream(arr).forEach((x) -> {
//            System.out.println(x);
        });

        //5.把参数声明和接口实现放在一行 即lambda表达式是匿名对象实现的一种新方式
        Arrays.stream(arr).forEach((x) -> System.out.println(x));

        //6.方法引用：通过方法引用的推导，省略参数声明和传递
        Arrays.stream(arr).forEach(System.out::println);

        //流式API对各种组件的自由装配
        IntConsumer outprintln = System.out::println;
        IntConsumer errprintln = System.err::println;
        Arrays.stream(arr).forEach(outprintln.andThen(errprintln));
    }
}
