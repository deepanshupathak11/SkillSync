package com.iamcodegym.SkillSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.iamcodegym.SkillSync")
public class SkillSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillSyncApplication.class, args);
	}

}
