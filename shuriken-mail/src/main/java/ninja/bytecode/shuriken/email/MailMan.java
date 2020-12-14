package ninja.bytecode.shuriken.email;

public class MailMan
{
	private String server;
	private int port;
	private boolean tls;
	private boolean ssl;
	private boolean smtpAuth;
	private String email;
	private String name;
	private String username;
	private String password;
	
	public MailMan(String server, int port)
	{
		this.server = server;
		this.port = port;
		tls = true;
		ssl = false;
		smtpAuth = true;
	}
	
	public MailMan smtpAuth(boolean v)
	{
		this.smtpAuth = v;
		return this;
	}
	
	public MailMan email(String v)
	{
		this.email = v;
		return this;
	}
	
	public MailMan name(String v)
	{
		this.name = v;
		return this;
	}
	
	public MailMan smtpAuth(String username, String password)
	{
		return smtpAuth(true).username(username).password(password);
	}
	
	public MailMan password(String v)
	{
		this.password = v;
		return this;
	}
	
	public MailMan username(String v)
	{
		this.username = v;
		return this;
	}
	
	public MailMan tls(boolean v)
	{
		this.tls = v;
		return this;
	}
	
	public MailMan ssl(boolean v)
	{
		this.ssl = v;
		return this;
	}

	public String getServer()
	{
		return server;
	}

	public int getPort()
	{
		return port;
	}

	public boolean isTls()
	{
		return tls;
	}

	public boolean isSsl()
	{
		return ssl;
	}

	public boolean isSmtpAuth()
	{
		return smtpAuth;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getEmail()
	{
		return email;
	}

	public String getName()
	{
		return name;
	}
}
