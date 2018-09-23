package spring.mytest.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CompileClassLoader extends ClassLoader {

	private String mLibPath;


	public CompileClassLoader(String path)
	{
		mLibPath=path;
	}

	/**
	 * 读取一个文件内容
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private byte[] getBytes(String fileName) throws IOException {
		File file=new File(fileName);
		long len=file.length();
		byte[] raw=new byte[(int)len];
		try(FileInputStream fin=new FileInputStream(file))
		{
			//一次性读取全部
			int r=fin.read(raw);
			if(r!=len)
			{
				throw new IOException("无法读取全部文件");
			}
			return raw;
		}
	}

	/**
	 * 编译指定Java文件的方法
	 * @param javaFile
	 * @return
	 */
	private boolean compileJavaFile(String javaFile) throws IOException {
		System.out.println("正在编译：" + javaFile + "...");
		Process p = Runtime.getRuntime().exec("javac -encoding UTF-8 " + javaFile);
		try {
			p.waitFor();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		//获取执行结果；0表示成功
		int ret = p.exitValue();
		return ret == 0;
	}

	/**
	 * 重写ClassLoader的findClass方法
	 * @param name
	 * @return
	 */
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class clazz = null;
		//将包路径中的(.)换成(/)
		String fileStub = name.replace(".", "/");
		String javaFileName = mLibPath + getFileName(name, "java");
		String classFileName = mLibPath + getFileName(name, "class");
		File javaFile = new File(javaFileName);
		File classFile = new File(classFileName);
		//当指定java存在且class不存在 或者 Java源文件的修改时间晚于Class文件时间，重新编译
		if (javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
			try {
				if (!compileJavaFile(javaFileName) || !classFile.exists()) {
					throw new ClassNotFoundException("ClassNotFoundException:" + javaFileName);

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		//如果class文件存在，系统负责将该文件转换为class对象
		if (classFile.exists()) {
			try {
				//将class文件的二进制数据读入数组
				byte[] raw = getBytes(classFileName);
				//调用classloader的defineclass方法将二进制数据转换成class对象
				clazz = defineClass(name, raw, 0, raw.length);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		//如果clazz为null，表明加载失败
		if (clazz == null) {
			throw new ClassNotFoundException("文件加载失败！");
		}

		return clazz;

	}

	//获取要加载 的class文件名
	private String getFileName(String name,String extend) {
		// TODO Auto-generated method stub
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return name + "." + extend;
		} else {
			return name.substring(index + 1) + "." + extend;
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

		String param="动态加载java成功！";
		String progClass = "jeffrey.dynamic.loader.HelloClassLoader"; //这里包名很重要。如果package名称和当前项目一致，spring不会走到指定以findclass中。
	/*	String[] progArgs=new String[arg.length-1];
		System.arraycopy(arg,1,progArgs,0,progArgs.length);*/
		CompileClassLoader ccl=new CompileClassLoader("c://Java//ExternalFile//ClassLoader");
		Class<?> clazz=ccl.loadClass(progClass);
		Object obj=clazz.newInstance();
		//获取需要运行的类的showParams方法
		Method method=clazz.getMethod("showParams", String.class);
		method.invoke(obj,param);//这里的obj不能为null。method只是一个框架，具体处理那个实例是通过obj指定。


	}


}
