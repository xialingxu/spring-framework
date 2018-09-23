package spring.mytest.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Dog
{
	void info();
	void run();
}

class Gundog implements Dog
{

	@Override
	public void info() {
		System.out.println("我是一只猎狗！");
	}

	@Override
	public void run() {
		System.out.println("我奔跑迅速！");
	}
}


class Dogutil
{
	//第一个拦截器方法
	public void method1()
	{
		System.out.println("=====模拟第一个通用方法=====");
	}

	//第二个拦截器方法
	public void method2()
	{
		System.out.println("=====模拟第二个通用方法=====");
	}
}

class MyInvokationHandlerEx implements InvocationHandler {

	//需要被代理的对象
	private Object object;
	public MyInvokationHandlerEx(Object _object)
	{
		this.object=_object;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("MyInvokationHandlerEx invoke begin");
		Dogutil dogutil=new Dogutil();
		dogutil.method1();
		Object result=method.invoke(object, args);
		dogutil.method2();
		return result;
	}
}

class MyProxyFactory
{
	public static Object getProxy(Object target)
	{
		MyInvokationHandlerEx myInvokationHandlerEx=new MyInvokationHandlerEx(target);
		ClassLoader classLoader=target.getClass().getClassLoader();
		Class<?>[] interfaces=target.getClass().getInterfaces();
		return Proxy.newProxyInstance(classLoader,interfaces,myInvokationHandlerEx);
	}
}


public class DynamicProxyEx {
	public static void main(String[] args)
	{
		Dog target=new Gundog();
		Dog dog=(Dog)MyProxyFactory.getProxy(target);
		dog.info();
		dog.run();
	}
}
