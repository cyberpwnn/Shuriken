package ninja.bytecode.shuriken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ninja.bytecode.shuriken.logging.L;

public class Test
{
	public static void main(String[] a)
	{
		SecretSauce s = new SecretSauce("mysecurepassword");
		
		for(Field i : ArrayList.class.getDeclaredFields())
		{
			L.v("Field: " + i.getType().getSimpleName() + ": Name: " + i.getName());
		}
	}
}
