package ninja.bytecode.shuriken.cloud.web;

@FunctionalInterface
public interface ParcelListener<IN, OUT>
{
	public OUT handle(IN in, OUT out);
}
