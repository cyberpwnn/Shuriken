package ninja.bytecode.shuriken.bukkit.tetris;

import ninja.bytecode.shuriken.bukkit.util.text.D;
import ninja.bytecode.shuriken.collections.Callback;
import ninja.bytecode.shuriken.math.M;
import ninja.bytecode.shuriken.math.Profiler;

public class JobBuilder
{
	private Job job;

	public JobBuilder(String jobID)
	{
		job = new TetrisJob(jobID);
	}

	public JobBuilder environment(JobEnvironment environment)
	{
		job.setJobEnvironment(environment);
		return this;
	}

	public JobBuilder ignoreCondition(JobIgnoreCondition ignoreCondition)
	{
		job.setIgnoreCondition(ignoreCondition);
		return this;
	}

	public JobBuilder urgency(JobUrgency urgency)
	{
		job.setUrgency(urgency);
		return this;
	}

	public JobBuilder minimumDelay(int minimumDelay)
	{
		job.setMinimumDelay(minimumDelay);
		return this;
	}

	public void schedule(Runnable r, Callback<JobResult> res)
	{
		JobScheduler.scheduler.schedule(commit(r), res);
	}

	public void schedule(Runnable r)
	{
		JobScheduler.scheduler.scheduleBlindly(commit(r));
	}

	public Job commit(Runnable r)
	{
		job.setRunnable(() ->
		{
			JobResult res = null;

			try
			{
				Profiler px = new Profiler();
				px.begin();
				r.run();
				px.end();
				JobScheduler.scheduler.getMetrics(job.getID()).log(px.getMilliseconds());
				res = new JobResult(px.getMilliseconds(), M.ms() - job.getScheduledTick(), JobResultStatus.COMPLETED);
			}

			catch(Throwable e)
			{
				e.printStackTrace();
				D.as("Job Executer").f("Failed to execute job " + job.getID());
				res = new JobResult(-1, M.ms() - job.getScheduledTick(), JobResultStatus.FAILED);
			}

			JobScheduler.scheduler.notifyJobResult(job, res);
		});

		return job;
	}
}
