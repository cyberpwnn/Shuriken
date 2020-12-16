package ninja.bytecode.shuriken.bukkit.api.scm;

import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenPermission;

public class PermissionSCM extends ShurikenPermission
{
	@Override
	protected String getNode()
	{
		return "scm";
	}

	@Override
	public String getDescription()
	{
		return "Allows the use of scm models";
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}
}
