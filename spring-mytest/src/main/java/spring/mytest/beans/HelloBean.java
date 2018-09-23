package spring.mytest.beans;

public class HelloBean {

	private String _name;
	private String _word;

	public HelloBean(String name,String word) {
      this._name=name;
      this._word=word;
	}

	public void showMe()
	{
		System.out.print("My name is :"+_name+".I will say :"+_word);
	}
}
