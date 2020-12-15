package ninja.bytecode.shuriken.web;

import ninja.bytecode.shuriken.collections.KList;

public interface Parcelable
{
	public String getParcelType();
	
	public KList<String> getParameterNames();
	
	public String getExample();
}
