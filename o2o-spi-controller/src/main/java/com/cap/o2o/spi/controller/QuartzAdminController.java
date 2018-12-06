package com.cap.o2o.spi.controller;

import com.cap.o2o.common.constants.CapO2oConstants;
import com.cap.o2o.entity.model.quartz.QuartzAdmin;
import com.cap.o2o.entity.response.StatusRes;
import com.cap.o2o.service.QuartAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ron
 * @date 2018/11/28
 */
@RestController
@RequestMapping("quartzAdmin")
public class QuartzAdminController {

    @Autowired
    private QuartAdminService quartAdminService;

    /**
     * 用户登录操作
     * @param admin
     * @return
     */
    @PostMapping("login")
    public StatusRes login(@RequestBody QuartzAdmin admin){
        StatusRes statusRes=new StatusRes(CapO2oConstants.FLAG_FALSE,"","","");
        statusRes = quartAdminService.queryAdmin(admin,statusRes);
        return statusRes;
    }

}
