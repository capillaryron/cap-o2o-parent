package com.cap.o2o.entity.response;

import lombok.*;

/**
 * Created by ron on 2018/11/28.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatusRes {

    private boolean success;
    private String code;
    private String msg;
    private Object bean;
}
