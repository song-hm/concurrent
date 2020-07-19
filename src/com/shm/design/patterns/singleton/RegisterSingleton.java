package com.shm.design.patterns.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设计单例注册表
 */
public class RegisterSingleton {

    //构建采用ConcurrentHashMap,用于充当缓存注册表
    private static final Map<String,Object> singletonObject = new ConcurrentHashMap<>();

    // 静态代码块只加载执行一次
    static {
        // 实例化Bean
        RegisterSingleton registerSingleton = new RegisterSingleton();

        //并注册到注册表中,key为类的完全限定名,value为实例化对象
        singletonObject.put(registerSingleton.getClass().getName(),registerSingleton);
    }


    /**
     * 私有化构造方法,避免外部创建本类实例
     */
    private RegisterSingleton(){

    }


    /**
     *  对外暴露获得该bean的方法,Spring框架一般会返回Object
     * @return
     */
    public static RegisterSingleton getInstance(String className){
        if (className==null){
            return null;
        }

        //从注册表获取,如果没有直接创建
        if (singletonObject.get(className) == null){
            try {
                //如果为空,通过反射进行实例化
                singletonObject.put(className,Class.forName(className).newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //从缓存表中回去,如果缓存命中直接返回
        return (RegisterSingleton) singletonObject.get(className);
    }
}
