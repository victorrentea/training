package victor.training.oo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericTypes {

	private List<String> list;
	
	public static void main(String[] args) throws Exception, SecurityException {
		Field field = GenericTypes.class.getDeclaredField("list");
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		System.out.println(type.getActualTypeArguments()[0]);;
	}
}
