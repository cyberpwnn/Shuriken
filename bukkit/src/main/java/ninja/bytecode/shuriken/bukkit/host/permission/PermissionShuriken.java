package ninja.bytecode.shuriken.bukkit.host.permission;

import ninja.bytecode.shuriken.bukkit.command.ShurikenPermission;

public class PermissionShuriken extends ShurikenPermission
{
	@Override
	protected String getNode()
	{
		return "shuriken";
	}

	@Override
	public String getDescription()
	{
		return "Shuriken permissions";
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}
}
