package com.tindev.tindevapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TindevApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TindevApiApplication.class, args);
	}


}
