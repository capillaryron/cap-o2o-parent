package com.cap.o2o.entity.model.quartz;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuartzAdmin implements Serializable{
    private static final long serialVersionUID = 1773463789781884810L;
    private Integer id;

    private String name;

    private String password;

    private Integer priority;

    private Date updateTime;

    private Date createTime;

}