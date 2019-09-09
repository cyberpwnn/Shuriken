package ninja.bytecode.shuriken.web;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.format.F;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.tools.JarTools;

public class ParcelWebServer
{
	private final ParcelWebServerConfiguration config;
	private final GList<Class<? extends Parcelable>> parcelables;
	private Server server;
	
	public ParcelWebServer()
	{
		parcelables = new GList<Class<? extends Parcelable>>();
		config = new ParcelWebServerConfiguration(this);
	}
	
	public String toString()
	{
		return "JPWS " + (configure().http() ? "HTTP:" + configure().httpPort() + " " : "") + (configure().https() ? "HTTPS:" + configure().httpsPort() + " " : "");
	}
	
	public void stop()
	{
		if(server != null && server.isRunning())
		{
			try
			{
				L.i("Stopping " + this);
				server.stop();
				L.i("Stopped " + this);
			}
			
			catch(Exception e)
			{
				L.ex(e);
			}
		}
	}
	
	public ParcelWebServer start()
	{
		stop();
		L.i("Starting " + this);
		server = new Server();
		ServletContextHandler api = new ServletContextHandler(server, configure().serverPath());
		api.setMaxFormContentSize(configure().maxFormContentSize());

		for(Class<? extends Parcelable> f : parcelables)
		{
			try
			{
				Parcel p = (Parcel) f.getConstructor().newInstance();
				api.addServlet(p.getClass(), "/" + p.getParcelType());
				L.i("Adding Servlet " + this + " /" + p.getParcelType() + " using " + p.getClass().getSimpleName());
			}
			
			catch(Throwable e)
			{
				L.ex(e);
			}
		}

		if(configure().https() && configure().http())
		{
			L.i("Configuring " + this + " for HTTP & HTTPS");
			server.setConnectors(new Connector[] {getHTTPConnector(), getHTTPSConnector()});
		}
		
		else if(configure().https())
		{
			L.i("Configuring " + this + " for HTTPS");
			server.setConnectors(new Connector[] {getHTTPSConnector()});
		}
		
		else if(configure().http())
		{
			L.i("Configuring " + this + " for HTTP");
			server.setConnectors(new Connector[] {getHTTPConnector()});
		}
		
		else
		{
			throw new RuntimeException("Cannot start a web server with both http and https protocols turned off!");
		}
		
		try
		{
			server.start();
			L.i(this + " Form Post Max: " + F.fileSize(configure().maxFormContentSize()));
			L.i(this + " Online!");
		}
		
		catch(Exception e)
		{
			L.ex(e);
			throw new RuntimeException("Failed to start webserver");
		}
		
		return this;
	}
	
	private ServerConnector getHTTPConnector()
	{
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(configure().httpPort());
		L.i("Creating a connector for HTTP/" + configure().httpPort());
		return connector;
	}

	@SuppressWarnings("deprecation")
	private ServerConnector getHTTPSConnector()
	{
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(configure().sslKeystore());
		sslContextFactory.setKeyStorePassword(configure().sslKeystorePassword());
		sslContextFactory.setKeyManagerPassword(configure().sslKeystoreKeyPassword());
		ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
		sslConnector.setPort(configure().httpsPort());
		L.i("Creating a connector for HTTPS/" + configure().httpsPort() + " secured with " + configure().sslKeystore() + "/" + configure().sslKeystoreKeyName());
		
		return sslConnector;
	}
	
	public ParcelWebServerConfiguration configure()
	{
		return config;
	}
	
	public ParcelWebServer addParcelables(Class<?> amchorPackage, String packagePrefix)
	{
		return addParcelables(JarTools.getClassesInPackage(JarTools.getJar(amchorPackage), packagePrefix, Parcelable.class));
	}
	
	public ParcelWebServer addParcelables(GList<Class<? extends Parcelable>> o)
	{
		for(Class<? extends Parcelable> i : o)
		{
			parcelables.add(i);
		}
		
		return this;
	}
	
	@SafeVarargs
	public final ParcelWebServer addParcelables(Class<? extends Parcelable>... o)
	{
		for(Class<? extends Parcelable> i : o)
		{
			parcelables.add(i);
		}
		
		return this;
	}
}
