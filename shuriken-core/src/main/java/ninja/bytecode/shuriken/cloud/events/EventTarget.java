package ninja.bytecode.shuriken.cloud.events;

import java.lang.reflect.Method;

import lombok.Data;

@Data
public class EventTarget
{
	private Object instance;
	private Method method;
	
	public EventTarget(Object instance, Method method)
	{
		this.instance = instance;
		this.method = method;
	}
	
	public void invoke(Event event) throws Throwable
	{
		method.invoke(instance, event);
	}
}
