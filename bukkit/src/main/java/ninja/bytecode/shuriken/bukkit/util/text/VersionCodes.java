package ninja.bytecode.shuriken.bukkit.util.text;

public class VersionCodes
{
	public static int getVersionCode(String version)
	{
		O<Integer> bit = new O<Integer>().set(1);
		O<Integer> code = new O<Integer>().set(0);

		for(char i : version.toCharArray())
		{
			try
			{
				code.set(code.get() + Integer.valueOf(i + "") * bit.get());
			}
			catch(Throwable e)
			{

			}
			bit.set(bit.get() + 1);
		}

		return code.get();
	}
}
