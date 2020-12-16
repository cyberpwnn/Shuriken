package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.scm.PermissionSCM;
import ninja.bytecode.shuriken.bukkit.bukkit.command.ShurikenPermission;
import ninja.bytecode.shuriken.bukkit.bukkit.command.Permission;

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
