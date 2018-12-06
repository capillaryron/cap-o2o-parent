package com.cap.o2o.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ron on 2018/12/4.
 *
 * jobName
 *            作业名称
 * jobGroupName
 *            作业组名称
 * riggerName
 *            触发器名称
 * riggerGroupName
 *            触发器组名称
 * clazz
 *            定时任务的class
 * cron
 *            时间表达式 void
 */
@Setter
@Getter
public class ScheduJobVO {

    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String clazz;
    private String cron;
}
