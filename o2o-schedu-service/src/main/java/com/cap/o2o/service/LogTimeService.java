package com.cap.o2o.service;

import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ron on 2018/11/30.
 */
public interface LogTimeService {

    public void addRecord(JoinPoint joinPoint, HttpServletRequest request,String excutionTime);
}
