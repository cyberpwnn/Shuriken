package ninja.bytecode.shuriken.web;

import java.io.File;
import java.io.PrintWriter;

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
import ninja.bytecode.shuriken.collections.GMap;
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

	public void generateMarkdownSpec(File md)
	{
		GMap<String, GMap<String, GList<String>>> ss = new GMap<>();
		
		L.v("=== Generating Parcel Spec ===");
		try
		{
			try
			{
				md.getParentFile().mkdirs();
			}

			catch(Throwable e)
			{

			}
			PrintWriter pw = new PrintWriter(md);
			pw.println("# Parcel Spec");
			pw.println();
			pw.println("This file was generated by Shuriken based on all parcel objects in the project.");
			pw.println();
			
			for(Class<? extends Parcelable> f : parcelables)
			{
				try
				{
					Parcelable p = f.getConstructor().newInstance();
					String category = "";
					ParcelRequest aq = f.getDeclaredAnnotation(ParcelRequest.class);
					ParcelResponse ar = f.getDeclaredAnnotation(ParcelResponse.class);
					String subcat = "";
					
					if(aq != null)
					{
						subcat = "Requests";
						category = "Parcels for " + (aq.value().trim().isEmpty() ? "Uncategorized" : aq.value());
					}
					
					else if(ar != null)
					{
						subcat = "Responses";
						category = "Parcels for " + (ar.value().trim().isEmpty() ? "Uncategorized" : ar.value());
					}
					
					else
					{
						subcat = "Other";
						category = "Uncategorized";
					}
					
					ParcelDescription d = f.getDeclaredAnnotation(ParcelDescription.class);
					String description = d != null ? d.value() : "No Description Provided";
					String w = "* [" + p.getClass().getSimpleName() + " at `/" + p.getParcelType() + "`" + "](#" + p.getParcelType() + ") " + description;
					ss.putThen(category, new GMap<>()).putThen(subcat, new GList<>()).add(w);
				}

				catch(Throwable e)
				{
					L.ex(e);
				}
			}

			ss.k().sort().forEach((l) -> {
				pw.println("## " + l);
				ss.get(l).k().sort().forEach((v) -> {
					pw.println("### " + v);
					ss.get(l).get(v).copy().sort().forEach((x) -> pw.println(x));
				});
				pw.println();
			});
			pw.println();

			for(Class<? extends Parcelable> f : parcelables)
			{
				try
				{
					Parcelable p = f.getConstructor().newInstance();
					ParcelDescription d = f.getDeclaredAnnotation(ParcelDescription.class);
					ParcelResponseSuccess s = f.getDeclaredAnnotation(ParcelResponseSuccess.class);
					ParcelResponseError e = f.getDeclaredAnnotation(ParcelResponseError.class);
					String description = d != null ? d.value() : "No Description Provided";
					Class<? extends Parcelable> successType = s != null ? s.type() : null;
					String sType = successType != null ? (successType.equals(FancyParcelable.class)) ? "HTML" : successType.getConstructor().newInstance().getParcelType() : "null";
					String successDescription = s != null ? s.reason() : "No Description Provided";
					Class<? extends Parcelable> errorType = e != null ? e.type() : null;
					String eType = errorType != null ? (successType.equals(FancyParcelable.class)) ? "HTML" : errorType.getConstructor().newInstance().getParcelType() : "null";
					String errorDescription = e != null ? e.reason() : "No Description Provided";
					GList<String> parameters = p.getParameterNames();
					pw.println("## `/" + p.getParcelType() + "`");
					pw.println("> " + description);
					pw.println();

					if(!parameters.isEmpty())
					{
						pw.println("#### Parameters");
						pw.println();
						pw.println("| Name      | Type | Description    |");
						pw.println("|-----------|------|----------------|");
						
						for(String i : parameters)
						{
							pw.println("| " + i + " |");
						}

						pw.println();
					}

					if(successType != null)
					{
						pw.println("**On Success** Responds with [" + sType + "](#" + sType + ") `" + successDescription + "`");
						pw.println();
					}

					if(errorType != null)
					{
						pw.println("**On Error** Responds with [" + eType + "](#" + eType + ") `" + errorDescription + "`");
						pw.println();
					}
					
					pw.println("---");
				}

				catch(Throwable e)
				{
					L.ex(e);
				}
			}

			pw.close();
		}

		catch(Throwable ee)
		{
			L.ex(ee);
		}

		L.v("=== ====================== ===");
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
