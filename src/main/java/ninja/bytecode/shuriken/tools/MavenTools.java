package ninja.bytecode.shuriken.tools;

import java.io.IOException;
import java.io.StringReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import ninja.bytecode.shuriken.io.VIO;

public class MavenTools
{
	public static String readPomFile() throws IOException
	{
		return readPomFile(MavenTools.class);
	}
	
	public static String readPomFile(Class<?> codeSource) throws IOException
	{
		return VIO.readAll(JarTools.readJarEntry("META-INF/maven/ninja.bytecode/Shuriken/pom.xml"));
	}
	
	public static MavenProject getProject() throws IOException, XmlPullParserException
	{
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		Model model = mavenreader.read(new StringReader(readPomFile()));
		return new MavenProject(model);
	}
}
