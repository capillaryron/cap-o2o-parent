package com.cap.o2o.schedu.factory;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ron on 2018/12/3.
 */
@Component
public class JobFactory extends AdaptableJobFactory{

    private AutowireCapableBeanFactory factory;

    public JobFactory(AutowireCapableBeanFactory factory) {
        this.factory = factory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        factory.autowireBean(job);
        return job;
    }
}
