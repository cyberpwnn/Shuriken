package ninja.bytecode.shuriken.bukkit.api.scm;

import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarPermission;

public class PermissionSCM extends MortarPermission
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
