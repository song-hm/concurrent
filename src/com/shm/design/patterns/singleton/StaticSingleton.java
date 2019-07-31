package com.shm.design.patterns.singleton;

/**
 * 同时拥有前两种方式的优点的单例模式
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingleton is create");
    }
    //巧妙使用内部类和类的初始化方法。内部类SingletonHolder声明为private，使得不可能在外部访问并初始化他
    //只可能在getInstance（）方法内部对SingletonHolder类进行初始化，利用虚拟机的类初始化机制创建单例。
    private static class SingletonHolder{
        private static StaticSingleton instance = new StaticSingleton();
    }

    //getInstance()方法无锁，保证高并发环境下性能优越
    //只在getInstance（）方法第一次被调用时，StaticSingleton的实例才会被创建
    public static StaticSingleton getInstance(){
        return SingletonHolder.instance;
    }
}
