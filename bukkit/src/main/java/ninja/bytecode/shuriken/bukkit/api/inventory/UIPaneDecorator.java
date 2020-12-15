package ninja.bytecode.shuriken.bukkit.api.inventory;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;
import ninja.bytecode.shuriken.bukkit.util.text.C;

import ninja.bytecode.shuriken.bukkit.api.world.MaterialBlock;

public class UIPaneDecorator extends UIStaticDecorator
{
	public UIPaneDecorator(C color)
	{
		super(new UIElement("c").setName(" ").setMaterial(new MaterialBlock(MaterialEnum.STAINED_GLASS_PANE.bukkitMaterial(), color.getItemMeta())));
	}
}
