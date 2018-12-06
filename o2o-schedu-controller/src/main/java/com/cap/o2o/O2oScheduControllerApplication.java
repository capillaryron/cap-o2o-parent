package com.cap.o2o;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class O2oScheduControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(O2oScheduControllerApplication.class, args);
	}
}
