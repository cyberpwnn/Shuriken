package ninja.bytecode.shuriken.bukkit.bukkit.plugin;

import ninja.bytecode.shuriken.bukkit.api.scm.PermissionSCM;
import ninja.bytecode.shuriken.bukkit.bukkit.command.MortarPermission;
import ninja.bytecode.shuriken.bukkit.bukkit.command.Permission;

public class PermissionMortar extends MortarPermission
{
	@Permission
	public PermissionSCM scm;

	@Override
	protected String getNode()
	{
		return "ninja/bytecode/shuriken/bukkit";
	}

	@Override
	public String getDescription()
	{
		return "Mortar permissions";
	}

	@Override
	public boolean isDefault()
	{
		return false;
	}
}
