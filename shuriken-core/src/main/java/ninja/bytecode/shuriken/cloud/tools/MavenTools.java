package ninja.bytecode.shuriken.cloud.tools;

import java.io.IOException;
import java.io.StringReader;

import ninja.bytecode.shuriken.cloud.io.IO;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class MavenTools
{
	public static String readPomFile() throws IOException
	{
		return readPomFile(MavenTools.class);
	}
	
	public static String readPomFile(Class<?> codeSource) throws IOException
	{
		return IO.readAll(JarTools.readJarEntry("META-INF/maven/ninja.bytecode/Shuriken/pom.xml"));
	}
	
	public static MavenProject getProject() throws IOException, XmlPullParserException
	{
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		Model model = mavenreader.read(new StringReader(readPomFile()));
		return new MavenProject(model);
	}
}
