package ninja.bytecode.shuriken.web;

import ninja.bytecode.shuriken.collections.GList;

public interface Parcelable
{
	public String getParcelType();
	
	public GList<String> getParameterNames();
}
