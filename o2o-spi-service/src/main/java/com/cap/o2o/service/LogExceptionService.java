package com.cap.o2o.service;

import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ron on 2018/11/30.
 */
public interface LogExceptionService {

    public void addRecord(JoinPoint joinPoint, HttpServletRequest request, Throwable e);
}
