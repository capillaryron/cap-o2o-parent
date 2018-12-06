package com.cap.o2o.dao;

import com.cap.o2o.entity.model.quartz.QuartzAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuartzAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuartzAdmin record);

    int insertSelective(QuartzAdmin record);

    QuartzAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuartzAdmin record);

    int updateByPrimaryKey(QuartzAdmin record);

    QuartzAdmin selectByAdminSelective(QuartzAdmin record);
}