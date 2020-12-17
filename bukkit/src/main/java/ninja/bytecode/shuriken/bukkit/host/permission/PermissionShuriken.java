package ninja.bytecode.shuriken.bukkit.host.permission;

import ninja.bytecode.shuriken.bukkit.scm.PermissionSCM;
import ninja.bytecode.shuriken.bukkit.command.ShurikenPermission;
import ninja.bytecode.shuriken.bukkit.command.Permission;

public class PermissionShuriken extends ShurikenPermission
{
	@Permission
	public PermissionSCM scm;

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
