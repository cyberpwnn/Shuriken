package ninja.bytecode.shuriken.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;

import ninja.bytecode.shuriken.collections.GList;
import ninja.bytecode.shuriken.io.VIO;
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
		VIO.fullTransfer(in, resp.getOutputStream(), 8192);
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

		if(ensureParameters(req, "d"))
		{
			try
			{
				write(resp, new Gson().toJson(new Gson().fromJson(req.getParameter("d"), getClass()).respond()));
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
				write(resp, error.toString(0));
			}
		}
		
		else
		{
			JSONObject error = new JSONObject();
			error.put("error", "Missing Data Parameter");
			write(resp, error.toString(0));
		}
	}
}
