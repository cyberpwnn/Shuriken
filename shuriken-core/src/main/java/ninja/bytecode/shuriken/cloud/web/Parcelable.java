package ninja.bytecode.shuriken.cloud.web;

import ninja.bytecode.shuriken.cloud.collections.KList;

public interface Parcelable
{
	public String getParcelType();
	
	public KList<String> getParameterNames();
	
	public String getExample();
}
