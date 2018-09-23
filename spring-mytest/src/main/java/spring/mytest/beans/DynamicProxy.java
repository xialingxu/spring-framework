package spring.mytest.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


interface Person
	{
		void walk();
		void sayHello(String name);
	}

	class Student implements Person
	{

		@Override
		public void walk() {
			System.out.println("Student is walking!");
		}

		@Override
		public void sayHello(String name) {
			System.out.println(name);
		}
	}

	class MyInvokationHandler implements InvocationHandler {

	    //需要被代理的对象
	    private Object object;
        public MyInvokationHandler(Object _object)
		{
			this.object=_object;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("MyInvocationHandler invoke begin");
			//System.out.println("proxy: " + proxy.getClass().getName());
			//System.out.println("method：" + method);
			if (args != null) {
				System.out.println("下面是执行该方法时传入的实参：");
				for (Object val : args) {
				//	System.out.println(val);
				}

			} else {
				//System.out.println("该调用方法没有实参！");
			}
			method.invoke(object, args);
			return null;
		}
	}
public class DynamicProxy {

		public static void main(String[] args)
		{
			Student student=new Student();
			InvocationHandler handler=new MyInvokationHandler(student);
			//指明被代理类实现的接口
			Class<?>[] interfaces = student.getClass().getInterfaces();
			ClassLoader loader=student.getClass().getClassLoader();
			Person p=(Person) Proxy.newProxyInstance(loader,interfaces,handler);
			p.walk();
			p.sayHello("孙悟空！");
		}
}
