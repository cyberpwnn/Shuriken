package ninja.bytecode.shuriken.email;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

import ninja.bytecode.shuriken.execution.J;
import ninja.bytecode.shuriken.io.IO;
import ninja.bytecode.shuriken.logging.L;

public class Email
{
	private boolean html;
	private String message;
	private String subject;
	private final String to;

	public static Email to(String to)
	{
		return new Email(to);
	}
	
	private Email(String to)
	{
		this.to = to;
	}

	public Email message(String message)
	{
		this.message = message;
		this.html = false;
		return this;
	}

	public Email html(String htmlPath)
	{
		try
		{
			this.message = IO.readAll(Email.class.getResourceAsStream(htmlPath));
			this.html = true;
		}

		catch(IOException e)
		{
			L.ex(e);
			return null;
		}

		return this;
	}
	
	public Email modify(String key, String replace)
	{
		this.message = this.message.replaceAll("\\Q%" + key + "%\\E", replace);
		return this;
	}

	public Email subject(String subject)
	{
		this.subject = subject;
		return this;
	}

	public boolean sendNow(MailMan man)
	{
		try
		{
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true); 
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", man.getServer());
			properties.put("mail.smtp.port", man.getPort() + "");
			properties.put("mail.imap.ssl.trust", "*");
			properties.put("mail.imap.ssl.socketFactory", sf);
			properties.put("mail.smtp.auth", man.isSmtpAuth() + "");
			properties.put("mail.smtp.ssl.enable", man.isSsl() + "");
			properties.put("mail.smtp.starttls.enable", man.isTls() + "");
			Session session = Session.getInstance(properties);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(man.getEmail(), man.getName()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			if(html)
			{
				message.setContent(this.message, "text/html");
			}
			
			else
			{
				message.setText(this.message);
			}
			
			message.setSubject(subject);
			message.saveChanges();
			Transport t = session.getTransport("smtp");
			t.connect(man.getServer(), man.getPort(), man.getUsername(), man.getPassword());
			t.sendMessage(message, message.getAllRecipients());
			return true;
		}

		catch(Throwable e)
		{
			L.ex(e);
		}

		return false;
	}

	public Future<Boolean> send(MailMan man)
	{
		return J.a(() -> sendNow(man));
	}
}
