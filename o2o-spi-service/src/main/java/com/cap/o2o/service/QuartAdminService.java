package com.cap.o2o.service;

import com.cap.o2o.entity.model.quartz.QuartzAdmin;
import com.cap.o2o.entity.response.StatusRes;

/**
 * Created by ron on 2018/11/28.
 */
public interface QuartAdminService {

    public StatusRes queryAdminById(int id, StatusRes statusRes);

    public StatusRes queryAdmin(QuartzAdmin admin, StatusRes statusRes);
}
