package com.shm.design.patterns.singleton;

/**
 * 单例模式
 */
public class Singleton {
    /**
     * 存在的问题，构造函数，即Singleton实例何时创建不受控制
     * 静态成员instance，会在类第一次初始化时的时候创建，这个时刻并不一定是getInstance()方法第一次被调用的时候
     * 情形：若该单例包含一个表示状态的静态成员。此时，在任何地方引用这个STATUS都会导致instance实例被创建。
     */
    public static int STATUS = 1;

    //把构造函数设置为private ,保证系统中不会有人意外创建多余实例，避免该类被错误创建
    private Singleton() {
        System.out.println("singleton is create");
    }
    //instance对象必须是private并且是static，private保证instance的安全性，否则一个意外就可能使的instance变为null
    //由于工厂方法getInstance（）必须是static的，因此instance也必须是static的。
    private static Singleton instance = new Singleton();
    public static Singleton getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        //任何对Singleton方法或字段的引用都会导致类初始化，并创建instance实例
        System.out.println(Singleton.STATUS);
        //打印结果：
        // singleton is create
        // 1

    }
}
