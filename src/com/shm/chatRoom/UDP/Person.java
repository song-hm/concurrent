package com.shm.chatRoom.UDP;

import java.io.Serializable;
public class Person implements Serializable{
    private static final long serialVersionUID = 1L;
    int age;
    String name;
    public Person(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Person [age=" + age + ", name=" + name + "]";
    }
}