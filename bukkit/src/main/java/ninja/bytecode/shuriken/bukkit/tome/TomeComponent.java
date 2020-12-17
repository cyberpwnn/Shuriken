package ninja.bytecode.shuriken.bukkit.tome;


import ninja.bytecode.shuriken.collections.KList;
import org.dom4j.Element;
import org.dom4j.Node;

public class TomeComponent implements WritableComponent, ReadableComponent
{
	private KList<TomeComponent> components;

	public TomeComponent()
	{
		this.components = new KList<>();
	}

	@Override
	public void read(Node thisElement)
	{

	}

	@Override
	public void construct(Element parent)
	{
		for(TomeComponent i : getComponents())
		{
			i.construct(parent);
		}
	}

	public KList<TomeComponent> getComponents()
	{
		return components;
	}

	public void setComponents(KList<TomeComponent> components)
	{
		this.components = components;
	}

	public void clearComponents()
	{
		getComponents();
	}

	public TomeComponent add(TomeComponent component)
	{
		getComponents().add(component);
		return this;
	}

	public TomeComponent add(String component)
	{
		getComponents().add(new TomeText(component));
		return this;
	}
}
