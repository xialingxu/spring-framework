package spring.mytest.beans;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class GetGenericInfo {
	private Map<String,Integer> score;
	public static void main(String[] args) throws NoSuchFieldException {
		Class<GetGenericInfo> clazz = GetGenericInfo.class;
		Field field = clazz.getDeclaredField("score");
		Class<?> a = field.getType();
		System.out.println("score的类型是：" + a);
		Type gType = field.getGenericType();
		if (gType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) gType;
			Type rType = parameterizedType.getRawType();
			System.out.println("原始类型是：" + rType);
			Type[] tArgs = parameterizedType.getActualTypeArguments();
			System.out.println("泛型类型是：");
			for (int i = 0; i < tArgs.length; i++) {
				System.out.println("第" + i + "个泛型类型是：" + tArgs[i]);
			}

		} else {
			System.out.println("获取泛型类型错误！");
		}

	}
}
