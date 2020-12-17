package ninja.bytecode.shuriken.random.noise;

import ninja.bytecode.shuriken.random.RNG;

@FunctionalInterface
public interface CNGFactory 
{
	CNG create(RNG seed);
}
