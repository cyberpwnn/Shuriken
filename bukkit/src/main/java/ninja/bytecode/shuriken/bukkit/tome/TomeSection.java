package ninja.bytecode.shuriken.bukkit.tome;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

public class TomeSection extends TomeComponent
{
	private String name;
	private boolean separate;

	public TomeSection()
	{
		this("");
		separate = false;
	}

	@Override
	public void read(Node thisElement)
	{
		Element e = (Element) thisElement;

		for(Attribute i : e.attributes())
		{
			if(i.getName().equals("name"))
			{
				setSectionName(i.getStringValue());
			}

			if(i.getName().equals("separate"))
			{
				setSeparate(Boolean.valueOf(i.getStringValue()));
			}
		}

		for(Iterator<Element> it = e.elementIterator(); it.hasNext();)
		{
			Element i = it.next();
			TomeComponent component = null;

			if(i.getName().equals("header"))
			{
				component = new TomeHeader();
			}

			else if(i.getName().equals("p"))
			{
				component = new TomeParagraph();
			}

			else if(i.getName().equals("ul"))
			{
				component = new TomeUnorederdList();
			}

			else if(i.getName().equals("ol"))
			{
				component = new TomeOrderedList();
			}

			else if(i.getName().equals("anchor"))
			{
				component = new TomeAnchor();
			}

			if(component != null)
			{
				component.read(i);
				add(component);
			}
		}
	}

	@Override
	public void construct(Element parent)
	{
		Element section = parent.addElement("section");
		section.addAttribute("name", getSectionName());
		section.addAttribute("separate", isSeparate() + "");

		for(TomeComponent i : getComponents())
		{
			i.construct(section);
		}
	}

	public TomeSection(String name)
	{
		this.name = name;
	}

	public String getSectionName()
	{
		return name;
	}

	public void setSectionName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isSeparate()
	{
		return separate;
	}

	public void setSeparate(boolean separate)
	{
		this.separate = separate;
	}
}
