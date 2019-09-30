package ninja.bytecode.shuriken.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.io.IO;
import ninja.bytecode.shuriken.json.JSONObject;
import ninja.bytecode.shuriken.logging.L;

public abstract class Parcel extends HttpServlet implements Parcelable, ParcelWebHandler
{
	private String type;
	private static final long serialVersionUID = 229675254360342497L;

	public Parcel(String parcelType)
	{
		this.type = parcelType;
	}

	@Override
	public String getParcelType()
	{
		return type;
	}

	protected boolean ensureParameters(HttpServletRequest r, String... pars)
	{
		for(String i : pars)
		{
			if(r.getParameter(i) == null)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
			on(req, resp);
		}

		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}

	protected void write(HttpServletResponse resp, InputStream in) throws IOException
	{
		IO.fullTransfer(in, resp.getOutputStream(), 8192);
		in.close();
	}

	protected void write(HttpServletResponse resp, String s) throws IOException
	{
		resp.getWriter().println(s);
	}

	@Override
	public String getNode()
	{
		return getParcelType();
	}

	public abstract Parcelable respond();

	@Override
	public void on(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("application/json");
		resp.setStatus(HttpStatus.OK_200);
		String d = null;

		if(ensureParameters(req, "d"))
		{
			d = req.getParameter("d");
		}

		else if(ensureParameters(req, "b"))
		{
			d = new String(IO.decode(req.getParameter("b")), StandardCharsets.UTF_8);
		}
		
		else
		{
			try
			{
				Map<String, String[]> m = req.getParameterMap();
				JSONObject j = new JSONObject();
				
				for(String i : m.keySet())
				{
					try
					{
						Field f = getClass().getDeclaredField(i);
						f.setAccessible(true);
						if(f.getType().equals(String.class))
						{
							j.put(i, req.getParameter(i));
						}
						
						else if(f.getType().equals(int.class))
						{
							j.put(i, Integer.valueOf(req.getParameter(i)));
						}
						
						else if(f.getType().equals(long.class))
						{
							j.put(i, Long.valueOf(req.getParameter(i)));
						}
						
						else if(f.getType().equals(boolean.class))
						{
							j.put(i, Boolean.valueOf(req.getParameter(i)));
						}
					}
					
					catch(Throwable e)
					{
						JSONObject error = new JSONObject();
						error.put("error", "Parameter Conversion Error");
						error.put("type", e.getClass().getCanonicalName());
						error.put("message", "Could not handle field " + i);
						write(resp, error.toString());
						return;
					}
				}
				
				if(!j.keySet().isEmpty())
				{
					d = j.toString(0);
				}
			}
			
			catch(Throwable e)
			{
				JSONObject error = new JSONObject();
				error.put("error", "Server Surface Error");
				error.put("type", e.getClass().getCanonicalName());
				error.put("message", "Could not handle web spread request");
				write(resp, error.toString());
				return;
			}
		}

		if(d != null)
		{
			try
			{
				Parcelable g = new Gson().fromJson(d, getClass()).respond();
				
				if(g instanceof FancyParcelable)
				{
					resp.setContentType("text/html");
					write(resp, ((FancyParcelable) g).getHTML());
				}
				
				else
				{
					write(resp, new Gson().toJson(g));
				}
			}

			catch(Throwable e)
			{
				String pars = "";
				boolean q = false;

				for(String i : new GList<String>(req.getParameterNames()))
				{
					q = true;
					pars += "&" + i + "=" + req.getParameter(i);
				}

				L.w("Server Exception when handling */" + getNode() + (q ? "?" : "") + pars.substring(1).trim());
				L.ex(e);
				JSONObject error = new JSONObject();
				error.put("error", "Server Surface Error");
				error.put("type", e.getClass().getCanonicalName());
				error.put("message", e.getMessage());
				error.put("data", d);
				write(resp, error.toString(0));
				return;
			}
		}

		else
		{
			JSONObject error = new JSONObject();
			error.put("error", "Missing Data Parameter (d for data, b for urlencoded) or missing parameters.");
			write(resp, error.toString(0));
			return;
		}
	}
}
