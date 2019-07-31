package com.shm.design.patterns.singleton;

/**
 * 精确控制instance的创建时间，支持延迟加载的策略，只会在instance第一次使用时创建对象
 */
public class LazySingleton {
    private LazySingleton() {
        System.out.println("LazySingleton is create");
    }
    //一开始并不实例化instance，当getInstance（）方法被第一次调用时，创建单例对象
    private static LazySingleton instance = null;
    //为防止对象被多次创建，不得不使用synchronized关键字进行方法同步
    //好处是充分利用了延迟加载，只在真正需要的时候创建对象
    //坏处是并发环境下加锁，竞争激烈的场合对性能可能有一定的影响
    public static synchronized LazySingleton getInstance(){
        if (instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }
}
