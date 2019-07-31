package com.shm.design.patterns.unchange;

/**
 * 不变模式
 * 1.去除setter方法及所有修改自身属性的方法
 * 2.将所有属性设置为私有，并用final标记，确保其不可修改。
 * 3.确保没有子类可以重载修改他的行为
 * 4.有一个可以创建完整对象的构造函数
 * JDK中不变模式应用广泛，最典型的是java.lang.String类。此外所有的元数据类、包装类都是使用不变模式实现的。
 * 使用不变模式后所有实例的方法均不进行同步操作，保证了他们在多线程环境下的性能
 * 不变模式通过回避问题而不是解决问题的态度来处理多线程环境下的并发访问控制
 */
//一个不变的产品对象
public final class Product { //final关键字确保无子类
    private final String num; //私有属性，不会被其他对象获取，final保证属性不会被两次赋值
    private final String name;
    private final double price;

    //在创建对象时，必须指定数据，因为创建后无法进行修改
    public Product(String num, String name, double price) {
        super();
        this.num = num;
        this.name = name;
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
