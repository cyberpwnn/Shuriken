package ninja.bytecode.shuriken.execution;

import ninja.bytecode.shuriken.bench.PrecisionStopwatch;
import ninja.bytecode.shuriken.format.F;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.math.RollingSequence;

public class Test {
	public static int k = 0;
	public static void main(String[] a)
	{
		Thread runner = new Thread(() -> {
			RollingSequence sq = new RollingSequence(3);
			int cycles = 0;
			ChronoLatch f = new ChronoLatch(1000);
			while(true)
			{
				
				cycles++;
				J.sleep(500);
				cycles++;
				PrecisionStopwatch ss = PrecisionStopwatch.start();
				for(int i = 0; i < 1000000 * Math.random(); i++)
				{
					k += i;
					
					if(f.flip())
					{
						L.w("Real: " + F.f(cycles) + " (" + F.duration(sq.getAverage(), 2) + ")");
					}
				}
				sq.put(ss.getMilliseconds());
			}
		});
		ThreadMonitor mon = ThreadMonitor.bind(runner);

		runner.start();
	}
}
