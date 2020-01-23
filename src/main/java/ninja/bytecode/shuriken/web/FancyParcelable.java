package ninja.bytecode.shuriken.web;

import ninja.bytecode.shuriken.collections.KList;

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
			public KList<String> getParameterNames()
			{
				return new KList<String>();
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
