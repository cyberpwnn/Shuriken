package ninja.bytecode.shuriken.cloud;

import ninja.bytecode.shuriken.cloud.service.IService;

public class SVC
{
	public static <T extends IService> T get(Class<? extends T> c)
	{
		return Shuriken.getService(c);
	}
}
