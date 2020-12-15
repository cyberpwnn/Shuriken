package ninja.bytecode.shuriken.bukkit.api.inventory;

import ninja.bytecode.shuriken.bukkit.bukkit.compatibility.MaterialEnum;

import ninja.bytecode.shuriken.bukkit.api.world.MaterialBlock;

public class UIRainbowDecorator implements WindowDecorator
{
	@Override
	public Element onDecorateBackground(Window window, int position, int row)
	{
		int apos = window.getRealPosition(position, row);

		return new UIElement("bh")
				.setBackground(true)
				.setName(" ")
				.setMaterial(
						new MaterialBlock(
								MaterialEnum.STAINED_GLASS_PANE.bukkitMaterial(),
								(byte) (apos % 15)));
	}
}
