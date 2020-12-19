package ninja.bytecode.shuriken.bukkit.host;

import ninja.bytecode.shuriken.bukkit.command.ShurikenSender;
import ninja.bytecode.shuriken.bukkit.test.ShurikenTestSuite;
import ninja.bytecode.shuriken.bukkit.world.Explosion;
import ninja.bytecode.shuriken.bukkit.world.Impulse;
import ninja.bytecode.shuriken.bukkit.world.Players;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.random.RNG;
import org.bukkit.command.CommandSender;

public class ShurikenAPITests extends ShurikenTestSuite {
    public ShurikenAPITests(ShurikenSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public String getName() {
        return "api";
    }

    public void logging()
    {
        sender.sendMessage("Logging tests to the console.");
        L.i("Info");
        L.f("FAILURE");
        L.e("ERROR");
        L.l("Logging");
        L.v("Verbose");
        L.w("Warning");
        L.l("---------");

        for(int i = 0; i < RNG.r.nextInt(8) + 3; i++)
        {
            L.l("This was logged a lot of times in a row.");
        }
    }

    public void impulsePush()
    {
        new Impulse(16).push().force(10).echo(7).damage(0)
                .punch(Players.targetBlock(sender.player(), 64));
    }

    public void almightyPush()
    {
        new Impulse(32).push().force(10).echo(100).damage(0)
                .punch(Players.targetBlock(sender.player(), 64));
    }

    public void almightyPull()
    {
        new Impulse(32).pull().force(10).echo(100).damage(0)
                .punch(Players.targetBlock(sender.player(), 64));
    }

    public void impulsePull()
    {
        new Impulse(16).pull().force(10).echo(27).damage(0)
                .punch(Players.targetBlock(sender.player(), 64));
    }

    public void explode()
    {
        new Explosion()
                .noBlocks()
                .radius(4f)
                .boom(Players.targetBlock(sender.player(), 64));
    }
}
