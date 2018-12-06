package com.cap.o2o.dao;

import com.cap.o2o.entity.model.log.LogTime;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogTimeMapper {
    int deleteByPrimaryKey(String id);

    int insert(LogTime record);

    int insertSelective(LogTime record);

    LogTime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LogTime record);

    int updateByPrimaryKey(LogTime record);
}