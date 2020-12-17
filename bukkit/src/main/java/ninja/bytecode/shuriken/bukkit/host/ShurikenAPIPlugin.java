package ninja.bytecode.shuriken.bukkit.host;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import ninja.bytecode.shuriken.bukkit.host.control.GhostWorldController;
import ninja.bytecode.shuriken.bukkit.host.permission.PermissionShuriken;
import ninja.bytecode.shuriken.bukkit.nms.Catalyst;
import ninja.bytecode.shuriken.bukkit.nms.NMP;
import ninja.bytecode.shuriken.bukkit.scm.CommandSCM;
import ninja.bytecode.shuriken.bukkit.host.command.CommandClearConsole;
import ninja.bytecode.shuriken.bukkit.host.command.CommandRift;
import ninja.bytecode.shuriken.bukkit.host.command.CommandShuriken;
import ninja.bytecode.shuriken.bukkit.plugin.*;
import ninja.bytecode.shuriken.bukkit.command.Command;
import ninja.bytecode.shuriken.bukkit.command.Permission;
import ninja.bytecode.shuriken.bukkit.compute.math.M;
import ninja.bytecode.shuriken.bukkit.plugin.plugin.*;
import ninja.bytecode.shuriken.collections.LKMap;
import org.bukkit.Bukkit;

import ninja.bytecode.shuriken.bukkit.config.Configurator;
import ninja.bytecode.shuriken.bukkit.sched.J;
import ninja.bytecode.shuriken.bukkit.scm.SCMController;
import ninja.bytecode.shuriken.bukkit.tetris.JobScheduler;
import ninja.bytecode.shuriken.bukkit.tome.CommandTome;
import ninja.bytecode.shuriken.bukkit.host.control.CacheController;
import ninja.bytecode.shuriken.bukkit.host.control.MojangProfileController;
import ninja.bytecode.shuriken.bukkit.host.control.RiftController;
import ninja.bytecode.shuriken.bukkit.util.queue.PhantomQueue;
import ninja.bytecode.shuriken.bukkit.util.queue.Queue;
import ninja.bytecode.shuriken.bukkit.util.text.C;
import ninja.bytecode.shuriken.bukkit.util.text.D;
import net.md_5.bungee.api.ChatColor;

public class ShurikenAPIPlugin extends ShurikenPlugin
{
	@Instance
	public static ShurikenAPIPlugin p;

	@Permission
	public static PermissionShuriken perm;

	@Command
	private CommandShuriken shuriken;

	@Command
	private CommandRift rift;

	@Command
	private CommandClearConsole cls;

	@Command
	private CommandSCM scm;

	@Control
	private CacheController cacheController;

	@Control
	private MojangProfileController mojangProfileController;

	@Control
	private RiftController riftController;

	@Control
	private GhostWorldController ghostWorldControlller;

	@Control
	private SCMController scmController;

	@Command
	private CommandTome tome;

	private static Queue<String> logQueue;
	private ShurikenConfig cfg;

	@Override
	public void start()
	{
		justStarted();
		Configurator.JSON.load(cfg = new ShurikenConfig(), getDataFile("config.json"));
		v("Configuration Loaded... Looks like we're in debug mode!");
		M.initTicking();
		v("Ticking Initiated");

		J.sr(() -> flushLogBuffer(), 20);
		J.ar(() -> M.uptickAsync(), 0);
		J.sr(() -> M.uptick(), 0);
		J.ar(() -> JobScheduler.scheduler.tick(), 0);
		J.sr(() -> JobScheduler.scheduler.tock(), 0);
		v("Updating & Log Flushing Initiated");
		startNMS();
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	private void justStarted()
	{
		File f = new File("server.properties");

		if(M.ms() - f.lastModified() < TimeUnit.SECONDS.toMillis(30))
		{
			ShurikenBukkit.STARTUP_LOAD = true;
		}

		else
		{
			ShurikenBukkit.STARTUP_LOAD = false;
		}
	}

	public ShurikenConfig getMortarConfig()
	{
		return cfg;
	}

	private void startNMS()
	{
		v("Selecting a suitable NMP Catalyst");
		NMP.host = Catalyst.host;

		if(NMP.host != null)
		{
			v("Starting " + NMP.host.getVersion() + " Catalyst");
			NMP.host.start();
			v("NMP Catalyst " + NMP.host.getVersion() + " Online");
		}
	}

	private void flushLogBuffer()
	{
		if(logQueue == null)
		{
			return;
		}

		LKMap<String, Integer> count = new LKMap<>();

		while(logQueue.hasNext())
		{
			String f = logQueue.next();

			if(!count.containsKey(f))
			{
				count.put(f, 0);
			}

			count.put(f, count.get(f) + 1);
		}

		for(String i : count.keySet())
		{
			if(count.get(i) > 1)
			{
				Bukkit.getConsoleSender().sendMessage(i + ChatColor.GOLD + " x" + count.get(i));
			}

			else
			{
				Bukkit.getConsoleSender().sendMessage(i);
			}
		}
	}

	@Override
	public void stop()
	{
		stopNMS();
		flushLogBuffer();
		JobScheduler.scheduler.completeNow();
	}

	private void stopNMS()
	{
		v("Stopping Catalyst Host");

		if(NMP.host == null)
		{
			v("Looks like there is no NMP host to shut down. Meh, whatever");
		}

		else
		{
			try
			{
				v("Stopping NMP host " + NMP.host.getVersion());
				NMP.host.stop();
				v("NMP host " + NMP.host.getVersion() + " Offline");
			}

			catch(Throwable e)
			{
				v("NMP host " + NMP.host.getVersion() + " is mostly Offline...");
			}
		}
	}

	@Override
	public String getTag(String t)
	{
		if(t.trim().isEmpty())
		{
			return ShurikenBukkit.tag(getName());
		}

		return ShurikenBukkit.tag(getName() + " " + C.GRAY + " - " + C.WHITE + t);
	}

	public void checkForUpdates(boolean install)
	{
		try
		{
			D.as("Mortar Updater").l("Checking for Updates");
			URL dl = new URL("https://raw.githubusercontent.com/VolmitSoftware/Mortar/master/release/Mortar.jar");
			URL url = new URL("https://raw.githubusercontent.com/VolmitSoftware/Mortar/master/version.txt");
			InputStream in = url.openStream();
			BufferedReader bu = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String version = bu.readLine().trim().toLowerCase();
			String current = getDescription().getVersion().trim().toLowerCase();
			in.close();
			bu.close();

			if(version.equals(current))
			{
				D.as("Mortar Updater").l("Mortar " + current + " is up to date!");
			}

			else
			{
				D.as("Mortar Updater").l("Updates are available: " + current + " -> " + version);
				HttpURLConnection con = (HttpURLConnection) dl.openConnection();
				HttpURLConnection.setFollowRedirects(false);
				con.setConnectTimeout(10000);
				con.setReadTimeout(10000);
				D.as("Mortar Updater").l("Downloading Update v" + version);
				InputStream inx = con.getInputStream();
				new File("plugins/update").mkdirs();
				File mortar = new File("plugins/update/" + getFile().getName());
				FileOutputStream fos = new FileOutputStream(mortar);
				byte[] buf = new byte[16819];
				int r = 0;

				while((r = inx.read(buf)) != -1)
				{
					fos.write(buf, 0, r);
				}

				fos.close();
				inx.close();
				con.disconnect();
				D.as("Mortar Updater").w("Update v" + version + " downloaded.");
				D.as("Mortar Updater").w("Restart to apply");
			}
		}

		catch(Throwable e)
		{
			D.as("Mortar Updater").f("Failed to check for updates.");
			if(ShurikenConfig.DEBUG_LOGGING)
			{
				e.printStackTrace();
			}
		}
	}

	public static void log(String string)
	{
		if(logQueue == null)
		{
			logQueue = new PhantomQueue<>();
		}

		logQueue.queue(string);
	}
}
