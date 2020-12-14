package ninja.bytecode.shuriken.cloud.service;

public interface IService
{
	public void onStart();
	
	public void onStop();
	
	public String getName();
}
