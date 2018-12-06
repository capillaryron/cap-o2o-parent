package com.cap.o2o.dao;

import com.cap.o2o.entity.model.log.LogException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogExceptionMapper {
    int deleteByPrimaryKey(String id);

    int insert(LogException record);

    int insertSelective(LogException record);

    LogException selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LogException record);

    int updateByPrimaryKeyWithBLOBs(LogException record);

    int updateByPrimaryKey(LogException record);
}