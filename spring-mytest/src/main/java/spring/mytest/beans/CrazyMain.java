package spring.mytest.beans;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class CrazyMain {

	public static void main(String[] args) throws IOException {
		//遍历、输出根类加载器加载的所有路径jar文件
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for (int i = 0; i < urls.length; i++) {
			System.out.println(urls[i].toExternalForm());
		}

		//获取系统类加载器
		ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
		System.out.println("系统类加载器：" + systemLoader);
		Enumeration<URL> eml=systemLoader.getResources("");
		while (eml.hasMoreElements())
		{
			System.out.println(eml.nextElement());
		}

		//获取系统类加载器的父类加载器，得到扩展类加载器
		ClassLoader extensionLoader=systemLoader.getParent();
		System.out.println("扩展类加载器："+extensionLoader);
		System.out.println("扩展类加载器路径："+System.getProperty("java.ext.dirs"));
		System.out.println("扩展类加载器de1parent："+extensionLoader.getParent());


	}
}
