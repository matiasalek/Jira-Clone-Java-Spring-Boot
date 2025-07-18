package com.matiasalek.jiraclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JiraCloneApplication {
	public static void main(String[] args) {
		SpringApplication.run(JiraCloneApplication.class, args);
	}
}