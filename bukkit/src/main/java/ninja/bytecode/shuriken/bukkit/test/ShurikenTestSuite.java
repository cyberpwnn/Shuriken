package ninja.bytecode.shuriken.bukkit.test;

import lombok.Data;
import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.collections.KList;
import ninja.bytecode.shuriken.logging.L;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

@Data
public abstract class ShurikenTestSuite
{
    protected final ShurikenSender sender;
    protected final String[] args;

    public ShurikenTestSuite(ShurikenSender sender, String[] args)
    {
        if(sender == null || args == null)
        {
            this.sender = null;
            this.args = null;
            return;
        }

        this.sender = sender;

        KList<String> n = KList.from(args);
        String node = n.pop();
        this.args = n.toArray(new String[0]);

        for(Method i : getClass().getDeclaredMethods())
        {
            i.setAccessible(true);
            if(i.getName().equals(node))
            {
                try {
                    i.invoke(this);
                } catch(Throwable e)
                {
                    sender.sendMessage("Error Testing " + node + "() Check the console!");
                    L.ex(e);
                }
            }
        }
    }

    public abstract String getName();
}