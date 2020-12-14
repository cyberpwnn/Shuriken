package ninja.bytecode.shuriken.cloud.maven;

public class MavenRepository
{
	private final String repository;
	
	public MavenRepository(String repository)
	{
		this.repository = repository;
	}

	public String getRepository()
	{
		return repository;
	}
}
