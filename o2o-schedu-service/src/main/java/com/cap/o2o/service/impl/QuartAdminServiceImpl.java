package com.cap.o2o.service.impl;

import com.cap.o2o.common.constants.CapO2oConstants;
import com.cap.o2o.common.constants.CapO2oReqConstans;
import com.cap.o2o.dao.QuartzAdminMapper;
import com.cap.o2o.entity.model.quartz.QuartzAdmin;
import com.cap.o2o.entity.response.StatusRes;
import com.cap.o2o.service.QuartAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ron
 * @date 2018/11/28
 */
@Service("quartAdminService")
public class QuartAdminServiceImpl implements QuartAdminService {

    @Autowired
    private QuartzAdminMapper quartzAdminMapper;

    @Override
    public StatusRes queryAdminById(int id, StatusRes statusRes) {
        QuartzAdmin quartzAdmin = quartzAdminMapper.selectByPrimaryKey(id);
        if (quartzAdmin!=null){
            statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
            statusRes.setCode(CapO2oReqConstans.CODE_200);
            statusRes.setBean(quartzAdmin);
        }
        return statusRes;
    }


    @Override
    public StatusRes queryAdmin(QuartzAdmin admin, StatusRes statusRes) {
        QuartzAdmin quartzAdmin = quartzAdminMapper.selectByAdminSelective(admin);
        if (quartzAdmin!=null){
            statusRes.setSuccess(CapO2oConstants.FLAG_TRUE);
            statusRes.setCode(CapO2oReqConstans.CODE_200);
            statusRes.setBean(quartzAdmin);
        }
        return statusRes;
    }
}
