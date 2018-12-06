package com.cap.o2o.spi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ron on 2018/11/26.
 */
@RestController
@RequestMapping("example")
public class ExampleController {

    @Value("${envirDetect}")
    private String envirDetect;

    @RequestMapping("hello")
    public String hello(){
        return envirDetect;
    }


}
