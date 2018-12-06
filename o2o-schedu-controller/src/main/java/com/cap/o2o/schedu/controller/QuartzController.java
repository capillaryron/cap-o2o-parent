package com.cap.o2o.schedu.controller;

import com.cap.o2o.common.constants.CapO2oConstants;
import com.cap.o2o.entity.response.StatusRes;
import com.cap.o2o.entity.vo.ScheduJobVO;
import com.cap.o2o.entity.vo.SchedulOldJobVO;
import com.cap.o2o.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ron on 2018/12/3.
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
	 * job列表页
	 *
	 * @return
	 * @throws SchedulerException
	 */
	@GetMapping(value="/list")
	public StatusRes list() throws SchedulerException {
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes = quartzService.getSchedulerJobInfo(statusRes);
		return statusRes;
	}


	/**
	 * 新增job
	 *
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@PostMapping(value="/add")
	public StatusRes add(@RequestBody ScheduJobVO scheduJobVO){
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes=quartzService.addJob(scheduJobVO,statusRes);
		return statusRes;
	}

	/**
	 * 编辑job
	 *
	 * @return
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	@PostMapping(value="/edit")
	public StatusRes edit(@RequestBody SchedulOldJobVO schedulOldJobVO) throws SchedulerException, ClassNotFoundException {
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes = quartzService.modifyJobTime(schedulOldJobVO,statusRes);
		return statusRes;
	}

	/**
	 * 暂停job
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 */
	@PostMapping(value="/pauseJob")
	public StatusRes pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroupName") String jobGroupName){
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes=quartzService.pauseJob(jobName, jobGroupName,statusRes);
		return statusRes;
	}

	/**
	 * 恢复job
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 */
	@RequestMapping(value="/resumeJob",method=RequestMethod.POST)
	public StatusRes resumeJob(@RequestParam("jobName") String jobName,@RequestParam("jobGroupName") String jobGroupName){
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes=quartzService.resumeJob(jobName, jobGroupName,statusRes);
		return statusRes;
	}


	@PostMapping(value="/deleteJob")
	public StatusRes deleteJob(@RequestBody SchedulOldJobVO schedulOldJobVO){
		StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
		statusRes = quartzService.removeJob(schedulOldJobVO, statusRes);
		return statusRes;
	}





}
