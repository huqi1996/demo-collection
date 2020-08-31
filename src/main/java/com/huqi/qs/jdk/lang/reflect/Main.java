package com.huqi.qs.jdk.lang.reflect;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author huqi
 */
public class Main {
    public static void main(String[] args) {

        // JDK动态代理
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(this.getClass().getName() + " ***** " + method + " ***** " + Arrays.toString(args));
                method.invoke(new Father(), args);
                return method.invoke(new Father("huqi"), args);
            }
        };
        Person person = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, invocationHandler);
        System.out.println(person.eat("apple"));

        // Cglib动态代理
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            /**
             * o：cglib生成的代理对象
             * method：被代理对象方法
             * objects：方法入参
             * methodProxy: 代理方法
             */
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("======插入前置通知======");
                Object object = methodProxy.invokeSuper(o, objects);
                System.out.println("======插入后者通知======");
                return object;
            }
        };// 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(Father.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(methodInterceptor);
        // 创建代理对象
        Father proxy = (Father) enhancer.create();
        // 通过代理对象调用目标方法
        System.out.println(proxy.eat("banana"));
        // 创建代理对象
        proxy = (Father) enhancer.create(new Class[]{String.class}, new Object[]{"huqi"});
        // 通过代理对象调用目标方法
        System.out.println(proxy.eat("banana"));
    }
}
