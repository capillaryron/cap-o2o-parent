package com.cap.o2o.service.logImpl;

import com.cap.o2o.dao.LogTimeMapper;
import com.cap.o2o.entity.model.log.LogTime;
import com.cap.o2o.service.LogTimeService;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ron on 2018/11/30.
 */
@Service("logTimeService")
public class LogTimeServiceImpl implements LogTimeService {

    @Autowired
    private LogTimeMapper logTimeMapper;

    @Override
    public void addRecord(JoinPoint joinPoint, HttpServletRequest request,String excutionTime) {
        String id = UUID.randomUUID().toString().replace("-", "");
        LogTime logTime=new LogTime();
        logTime.setId(id);
        logTime.setMethodClass(joinPoint.getSignature().getDeclaringTypeName());
        logTime.setMethodName(joinPoint.getSignature().getName());
        logTime.setRequestUri(request.getRequestURI());
        logTime.setRequestType(request.getMethod());
        logTime.setRequestIp(request.getRemoteAddr());
        logTime.setExecutionTime(excutionTime);
        logTime.setUpdateTime(new Date());
        logTime.setCreateTime(new Date());
       logTimeMapper.insert(logTime);
    }
}
