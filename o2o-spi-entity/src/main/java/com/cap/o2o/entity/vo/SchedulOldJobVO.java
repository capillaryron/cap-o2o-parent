package com.cap.o2o.entity.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ron on 2018/12/4.
 */
@Setter
@Getter
public class SchedulOldJobVO extends ScheduJobVO{
    private String oldjobName;
    private String oldjobGroup;
    private String oldtriggerName;
    private String oldtriggerGroup;

}
