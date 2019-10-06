package ninja.bytecode.shuriken.web;

import ninja.bytecode.shuriken.collections.GList;

public interface FancyParcelable extends Parcelable
{
	public String getHTML();
	
	public static FancyParcelable of(String html)
	{
		return new FancyParcelable()
		{
			@Override
			public String getParcelType()
			{
				return "fancyparcel";
			}
			
			@Override
			public GList<String> getParameterNames()
			{
				return new GList<String>();
			}
			
			@Override
			public String getHTML()
			{
				return html;
			}

			@Override
			public String getExample()
			{
				return "";
			}
		};
	}
}
