package com.cap.o2o.service.impl;

import com.cap.o2o.common.constants.CapO2oConstants;
import com.cap.o2o.common.constants.CapO2oReqConstans;
import com.cap.o2o.entity.response.StatusRes;
import com.cap.o2o.entity.vo.ScheduJobVO;
import com.cap.o2o.entity.vo.SchedulOldJobVO;
import com.cap.o2o.job.JobEntity;
import com.cap.o2o.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("quartzService")
public class QuartzServiceImpl implements QuartzService {

	@Autowired
	@Qualifier("Scheduler")
	private Scheduler quartzScheduler;

	@Override
	public StatusRes getSchedulerJobInfo(StatusRes statusRes) throws SchedulerException {
		List<JobEntity> jobInfos = new ArrayList<JobEntity>();
		List<String> triggerGroupNames = quartzScheduler.getTriggerGroupNames();
		for (String triggerGroupName : triggerGroupNames) {
			Set<TriggerKey> triggerKeySet = quartzScheduler
					.getTriggerKeys(GroupMatcher
							.triggerGroupEquals(triggerGroupName));
			for (TriggerKey triggerKey : triggerKeySet) {
				Trigger t = quartzScheduler.getTrigger(triggerKey);
				if (t instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger) t;
					JobKey jobKey = trigger.getJobKey();
					JobDetail jd = quartzScheduler.getJobDetail(jobKey);
					JobEntity jobInfo = new JobEntity();
					jobInfo.setJobName(jobKey.getName());
					jobInfo.setJobGroup(jobKey.getGroup());
					jobInfo.setTriggerName(triggerKey.getName());
					jobInfo.setTriggerGroupName(triggerKey.getGroup());
					jobInfo.setCronExpr(trigger.getCronExpression());
					jobInfo.setNextFireTime(trigger.getNextFireTime());
					jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
					jobInfo.setStartTime(trigger.getStartTime());
					jobInfo.setEndTime(trigger.getEndTime());
					jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
					// jobInfo.setDuration(Long.parseLong(jd.getDescription()));
					Trigger.TriggerState triggerState = quartzScheduler
							.getTriggerState(trigger.getKey());
					jobInfo.setJobStatus(triggerState.toString());
					// NONE无,
					// NORMAL正常,
					// PAUSED暂停,
					// COMPLETE完全,
					// ERROR错误,
					// BLOCKED阻塞
					JobDataMap map = quartzScheduler.getJobDetail(jobKey)
							.getJobDataMap();
					if (null != map&&map.size() != 0) {
						jobInfo.setCount(Integer.parseInt((String) map
								.get("count")));
						jobInfo.setJobDataMap(map);
					} else {
						jobInfo.setJobDataMap(new JobDataMap());
					}
					jobInfos.add(jobInfo);
				}
				statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
				statusRes.setCode(CapO2oReqConstans.CODE_200);
				statusRes.setBean(jobInfos);
			}
		}
		return statusRes;
	}
	
	@Override
	public StatusRes addJob(ScheduJobVO scheduJob, StatusRes statusRes) {

		try {
			Class cls = Class.forName(scheduJob.getClazz());
			addJob(scheduJob, cls);
			statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
			statusRes.setCode(CapO2oConstants.JOB_ADD_SUC_100);
			statusRes.setMsg(CapO2oConstants.JOB_ADD_SUC);
		} catch (Exception e) {
			statusRes.setCode(CapO2oConstants.JOB_ADD_FAL_101);
			statusRes.setMsg(CapO2oConstants.JOB_ADD_FAL);
			e.printStackTrace();
		}
		return statusRes;
	}

	private void addJob(ScheduJobVO scheduJob, Class cls) throws SchedulerException {
		// 获取调度器
		Scheduler sched = quartzScheduler;
		// 创建一项作业
		JobDetail job = JobBuilder.newJob(cls)
                .withIdentity(scheduJob.getJobName(), scheduJob.getJobGroupName()).build();
		// 创建一个触发器
		CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(scheduJob.getTriggerName(), scheduJob.getTriggerGroupName())
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduJob.getCron()))
                .build();
		// 告诉调度器使用该触发器来安排作业
		sched.scheduleJob(job, trigger);
		// 启动
		if (!sched.isShutdown()) {
            sched.start();
        }
	}

	/**
	 * 修改定时器任务信息
	 */
	@Override
	public StatusRes modifyJobTime(SchedulOldJobVO schedulOldJob, StatusRes statusRes) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(schedulOldJob.getOldtriggerName(), schedulOldJob.getOldtriggerGroup()));
			if (trigger == null) {
				statusRes.setCode(CapO2oConstants.JOB_EDIT_FAL_103);
				statusRes.setMsg(CapO2oConstants.JOB_EDIT_FAL);
				return statusRes;
			}

			JobKey jobKey = JobKey.jobKey(schedulOldJob.getOldjobName(), schedulOldJob.getOldjobGroup());
			TriggerKey triggerKey = TriggerKey.triggerKey(schedulOldJob.getOldtriggerName(),
					schedulOldJob.getOldtriggerGroup());

			JobDetail job = sched.getJobDetail(jobKey);
			Class jobClass = job.getJobClass();
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);

			ScheduJobVO scheduJobVO=new ScheduJobVO();
			scheduJobVO.setJobName(schedulOldJob.getJobName());
			scheduJobVO.setJobGroupName(schedulOldJob.getJobGroupName());
			scheduJobVO.setTriggerName(schedulOldJob.getTriggerName());
			scheduJobVO.setTriggerGroupName(schedulOldJob.getTriggerGroupName());
			scheduJobVO.setCron(schedulOldJob.getCron());
			
			addJob(scheduJobVO,jobClass);
			statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
			statusRes.setCode(CapO2oConstants.JOB_EDIT_SUC_102);
			statusRes.setMsg(CapO2oConstants.JOB_EDIT_SUC);
		} catch (Exception e) {
			statusRes.setCode(CapO2oConstants.JOB_EDIT_FAL_103);
			statusRes.setMsg(CapO2oConstants.JOB_EDIT_FAL);
			e.printStackTrace();
		}
		return statusRes;
	}

	@Override
	public StatusRes pauseJob(String jobName, String jobGroupName,StatusRes statusRes) {
		try {
			if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName)){
				statusRes.setCode(CapO2oConstants.PARAM_IS_EMPTY_201);
				statusRes.setMsg(CapO2oConstants.PARAM_IS_EMPTY);
				return statusRes;
			}
			quartzScheduler.pauseJob( JobKey.jobKey(jobName, jobGroupName));
			statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
			statusRes.setCode(CapO2oConstants.JOB_PAU_SUC_104);
			statusRes.setMsg(CapO2oConstants.JOB_PAU_SUC);
		} catch (SchedulerException e) {
			statusRes.setCode(CapO2oConstants.JOB_PAU_FAL_105);
			statusRes.setMsg(CapO2oConstants.JOB_PAU_FAL);
			e.printStackTrace();
		}
		return statusRes;
	}

	@Override
	public StatusRes resumeJob(String jobName, String jobGroupName,StatusRes statusRes) {
		try {
			if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName)){
				statusRes.setCode(CapO2oConstants.PARAM_IS_EMPTY_201);
				statusRes.setMsg(CapO2oConstants.PARAM_IS_EMPTY);
				return statusRes;
			}
			quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
			statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
			statusRes.setCode(CapO2oConstants.JOB_RESU_SUC_106);
			statusRes.setMsg(CapO2oConstants.JOB_RESU_SUC);
		} catch (SchedulerException e) {
			statusRes.setCode(CapO2oConstants.JOB_RESU_FAL_107);
			statusRes.setMsg(CapO2oConstants.JOB_RESU_FAL);
			e.printStackTrace();
		}
		return statusRes;
	}

	@Override
	public StatusRes removeJob(SchedulOldJobVO schedulOldJobVO,StatusRes statusRes) {
		try {
			String jobName=schedulOldJobVO.getOldjobName();
			String jobGroupName=schedulOldJobVO.getOldjobGroup();
			String triggerName=schedulOldJobVO.getOldtriggerName();
			String triggerGroupName=schedulOldJobVO.getOldtriggerGroup();

			if(StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroupName) ||
					StringUtils.isEmpty(triggerName) || StringUtils.isEmpty(triggerGroupName) ){
				statusRes.setCode(CapO2oConstants.PARAM_IS_EMPTY_201);
				statusRes.setMsg(CapO2oConstants.PARAM_IS_EMPTY);
				return statusRes;
			}
			Scheduler sched = quartzScheduler;
			// 停止触发器
			sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 移除触发器
			sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
					triggerGroupName));
			// 删除任务
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
			statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
			statusRes.setCode(CapO2oConstants.JOB_DEL_SUC_108);
			statusRes.setMsg(CapO2oConstants.JOB_DEL_SUC);
		} catch (Exception e) {
			statusRes.setCode(CapO2oConstants.JOB_DEL_FAL_109);
			statusRes.setMsg(CapO2oConstants.JOB_DEL_FAL);
			e.printStackTrace();
		}
		return statusRes;
	}



	@Override
	public void modifyJobTime(String triggerName, String triggerGroupName,
			String time) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
					.triggerKey(triggerName, triggerGroupName));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.getTriggerBuilder()
						.withSchedule(CronScheduleBuilder.cronSchedule(time))
						.build();
				// 重启触发器
				sched.resumeTrigger(TriggerKey.triggerKey(triggerName,
						triggerGroupName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public void startSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdownSchedule() {
		try {
			Scheduler sched = quartzScheduler;
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
