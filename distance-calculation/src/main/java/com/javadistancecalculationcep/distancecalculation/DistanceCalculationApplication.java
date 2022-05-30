package com.javadistancecalculationcep.distancecalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
@EnableFeignClients
@SpringBootApplication
public class DistanceCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistanceCalculationApplication.class, args);
	}

}
