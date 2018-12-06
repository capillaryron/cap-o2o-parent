package com.cap.o2o.service.logImpl;

import com.cap.o2o.dao.LogExceptionMapper;
import com.cap.o2o.entity.model.log.LogException;
import com.cap.o2o.service.LogExceptionService;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ron on 2018/11/30.
 */
@Service("logExceptionService")
public class LogExceptionServiceImpl implements LogExceptionService {

    @Autowired
    private LogExceptionMapper logExceptionMapper;

    @Override
    public void addRecord(JoinPoint joinPoint, HttpServletRequest request, Throwable e) {
        String id = UUID.randomUUID().toString().replace("-", "");
        LogException logException=new LogException();
        logException.setId(id);
        logException.setMethodClass(joinPoint.getSignature().getDeclaringTypeName());
        logException.setMethodName(joinPoint.getSignature().getName());
        logException.setRequestUri(request.getRequestURI());
        logException.setRequestType(request.getMethod());
        logException.setRequestIp(request.getRemoteAddr());
        Object[] args = joinPoint.getArgs();
        if (args!=null) {
            logException.setRequestArgs(Arrays.toString(args));
        }
        logException.setExceptionTime(new Date());
        logException.setExceptionType(e.getClass().getName());
        logException.setExceptionMsg(e.getMessage());
        logException.setUpdateTime(new Date());
        logException.setCreateTime(new Date());
        logExceptionMapper.insert(logException);
    }
}
