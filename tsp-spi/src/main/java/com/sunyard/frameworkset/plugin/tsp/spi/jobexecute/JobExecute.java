package com.sunyard.frameworkset.plugin.tsp.spi.jobexecute;


import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;

public abstract class JobExecute {
	private Boolean isPause = false;

	public Boolean getIsPause () {
		return isPause;
	}

	public void setIsPause (Boolean isPause) {
		this.isPause = isPause;
	}

	/**
	 * 执行作业
	 *
	 * @param jobinst
	 * @return
	 */
	public abstract ResultMessage execute (JobInst jobinst);

	/**
	 * 执行器对应的类型
	 *
	 * @return
	 */
	public abstract String getType ();

	public abstract void stop ();

	public abstract void pause ();
}
