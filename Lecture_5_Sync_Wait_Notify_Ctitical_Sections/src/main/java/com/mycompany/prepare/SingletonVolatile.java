package com.mycompany.prepare;


// Lazy initialization, Spring beans are singletons by default
public class SingletonVolatile {

    private static volatile SingletonVolatile instance = null;

    private SingletonVolatile() {
    }

    public static SingletonVolatile getInstance() {
        if (instance == null) {
            synchronized (SingletonVolatile.class){
                if (instance == null) {
                    instance = new SingletonVolatile();
                }
            }
        }
        return instance ;
    }
}