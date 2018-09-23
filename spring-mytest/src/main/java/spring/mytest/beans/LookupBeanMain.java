package spring.mytest.beans;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LookupBeanMain {
	public static void main(String[] args)
	{
		ApplicationContext bf=new ClassPathXmlApplicationContext("lookupTest.xml");

		String a="";
	}
}
