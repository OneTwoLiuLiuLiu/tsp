package com.sunyard.frameworkset.plugin.tsp.manager.biz.dtf;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.*;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.*;
import com.sunyard.frameworkset.util.converter.BeanConverter;

/**
 * Created by Misaki on 2017/1/5.
 */
public class JobTranslator<S,T> {
	public JobConfigVo transfer (S s) throws InstantiationException, IllegalAccessException {
		if (s == null) {
			return new JobConfigVo ();
		} else {
			JobConfigVo t = getVo (s);
			return BeanConverter.convert (t, s);
		}
	}

	public JobConfig transferVo (T t) throws InstantiationException, IllegalAccessException{
		if (t == null) {
			return new JobConfig();
		} else {
			JobConfig s = getPo(t);
			return BeanConverter.convert (s, t);
		}
	}

	private JobConfigVo getVo (S s) throws IllegalAccessException, InstantiationException {
		if (("BatJobConfig").equals (s.getClass ().getSimpleName ())) {
			return BatJobConfigVo.class.newInstance ();
		} else if (("DataStageJobConfig").equals (s.getClass ().getSimpleName ())) {
			return DataStageJobConfigVo.class.newInstance ();
		}  else if (("ExeJobConfig").equals (s.getClass ().getSimpleName ())) {
			return ExeJobConfigVo.class.newInstance ();
		} else if (("HttpJobConfig").equals (s.getClass ().getSimpleName ())) {
			return HttpJobConfigVo.class.newInstance ();
		} else if (("JarJobConfig").equals (s.getClass ().getSimpleName ())) {
			return JarJobConfigVo.class.newInstance ();
		} else if (("KjbJobConfig").equals (s.getClass ().getSimpleName ())) {
			return KjbJobConfigVo.class.newInstance ();
		} else if (("ParallelJobConfig").equals (s.getClass ().getSimpleName ())) {
			return ParallelJobConfigVo.class.newInstance ();
		} else if (("PlanJobConfig").equals (s.getClass ().getSimpleName ())) {
			return PlanJobConfigVo.class.newInstance ();
		} else if (("SerialJobConfig").equals (s.getClass ().getSimpleName ())) {
			return SerialJobConfigVo.class.newInstance ();
		} else if (("ShellJobConfig").equals (s.getClass ().getSimpleName ())) {
			return ShellJobConfigVo.class.newInstance ();
		} else if (("StoreProJobConfig").equals (s.getClass ().getSimpleName ())) {
			return StoreProJobConfigVo.class.newInstance ();
		}else if("KtrJobConfig".equals(s.getClass().getSimpleName())){
			return KtrJobConfigVo.class.newInstance ();
		}else if("TurnOverJobConfig".equals(s.getClass().getSimpleName())){
			return TurnoverJobConfigVo.class.newInstance ();
		}else if("TaskJobConfig".equals(s.getClass().getSimpleName())){
			return TaskJobConfigVo.class.newInstance ();
		}
		return JobConfigVo.class.newInstance ();
	}

	private JobConfig getPo (T t) throws IllegalAccessException, InstantiationException {
		if (("BatJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return BatJobConfig.class.newInstance ();
		} else if (("DataStageJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return DataStageJobConfig.class.newInstance ();
		}  else if (("ExeJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return ExeJobConfig.class.newInstance ();
		} else if (("HttpJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return HttpJobConfig.class.newInstance ();
		} else if (("JarJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return JarJobConfig.class.newInstance ();
		} else if (("KjbJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return KjbJobConfig.class.newInstance ();
		} else if (("ParallelJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return ParallelJobConfig.class.newInstance ();
		} else if (("PlanJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return PlanJobConfig.class.newInstance ();
		} else if (("SerialJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return SerialJobConfig.class.newInstance ();
		} else if (("ShellJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return ShellJobConfig.class.newInstance ();
		} else if (("StoreProJobConfigVo").equals (t.getClass ().getSimpleName ())) {
			return StoreProJobConfig.class.newInstance ();
		}else if("KtrJobConfigVo".equals(t.getClass().getSimpleName())){
			return KtrJobConfig.class.newInstance ();
		}else if("TurnoverJobConfigVo".equals(t.getClass().getSimpleName())){
			return TurnOverJobConfig.class.newInstance ();
		}else if("TaskJobConfigVo".equals(t.getClass().getSimpleName())){
		return TaskJobConfig.class.newInstance ();
	    }
		return JobConfig.class.newInstance ();
	}



}
