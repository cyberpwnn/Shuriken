package ninja.bytecode.shuriken.random.noise.stream;

public interface ProceduralLayer
{
	public long getSeed();

	public double getOffsetX();

	public double getOffsetY();

	public double getOffsetZ();

	public double getZoom();
}
