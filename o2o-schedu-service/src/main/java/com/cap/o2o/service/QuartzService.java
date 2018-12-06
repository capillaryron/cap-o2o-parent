package com.cap.o2o.service;

import com.cap.o2o.entity.response.StatusRes;
import com.cap.o2o.entity.vo.ScheduJobVO;
import com.cap.o2o.entity.vo.SchedulOldJobVO;
import org.quartz.SchedulerException;

public interface QuartzService {

	/**
	 * 任务列表
	 * @return
	 * @throws SchedulerException
	 */
	public StatusRes getSchedulerJobInfo(StatusRes statusRes) throws SchedulerException;

	/**
	 * 添加任务
	 * @exception
	 * @since 1.0.0
	 */
	public StatusRes addJob(ScheduJobVO scheduJobVO,StatusRes statusRes);

	/**
	 * 修改任务时间
	 * @param schedulOldJobVO
	 * @return
	 */
	public StatusRes modifyJobTime(SchedulOldJobVO schedulOldJobVO,StatusRes statusRes);

	/**
	 * 修改触发器调度时间
	 * @param triggerName  触发器名称
	 * @param triggerGroupName  触发器组名称
	 * @param cron cron表达式
	 */
	public void modifyJobTime(String triggerName,
                              String triggerGroupName, String cron);

	
	/**
	 * 暂停指定的任务
	 * @param jobName 任务名称
	 * @param jobGroupName 任务组名称 
	 * @return
	 */
	public StatusRes pauseJob(String jobName, String jobGroupName,StatusRes statusRes);
	
	/**
	 * 恢复指定的任务
	 * @param jobName 任务名称
	 * @param jobGroupName 任务组名称 
	 * @return
	 */
	public StatusRes resumeJob(String jobName, String jobGroupName,StatusRes statusRes);

	/**
	 * 删除指定组任务
	 *
	 */
	public StatusRes removeJob(SchedulOldJobVO schedulOldJobVO,StatusRes statusRes);

	
	/**
	 * 开始所有定时任务。启动调度器
	 */
	public void startSchedule();

	/**
	 * 关闭调度器
	 */
	public void shutdownSchedule();
}
