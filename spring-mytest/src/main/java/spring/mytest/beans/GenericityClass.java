package spring.mytest.beans;

import java.util.Date;

class OldObjectFactory
{
	public static Object getInstance(String clsName)
	{
          try
		  {
		  	Class cls=Class.forName(clsName);
		  	return cls.newInstance();
		  }
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
			  return null;
		  }

	}
}

class NewObjectFactory
{
	public static <T> T getInstance(Class<T> cls)
	{
		try
		{
			return cls.newInstance();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

	}
}

public class GenericityClass {
	public static void main(String[] args)
	{
		Date d1=(Date)OldObjectFactory.getInstance("java.util.Date");
		Date d2=NewObjectFactory.getInstance(Date.class);

	}
}
