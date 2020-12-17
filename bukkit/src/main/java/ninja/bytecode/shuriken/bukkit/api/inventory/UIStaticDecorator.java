package ninja.bytecode.shuriken.bukkit.api.inventory;

import ninja.bytecode.shuriken.bukkit.compatibility.MaterialEnum;

import ninja.bytecode.shuriken.bukkit.api.world.MaterialBlock;

public class UIStaticDecorator implements WindowDecorator
{
	private Element element;

	public UIStaticDecorator(Element element)
	{
		this.element = element == null ? new UIElement("bg").setMaterial(new MaterialBlock(MaterialEnum.AIR.bukkitMaterial())) : element;
	}

	@Override
	public Element onDecorateBackground(Window window, int position, int row)
	{
		return element;
	}
}
