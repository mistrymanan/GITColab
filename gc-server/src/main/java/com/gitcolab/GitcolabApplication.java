package com.gitcolab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GitcolabApplication {

	public static void main(String[] args) {
		System.out.println("GitColab Application is running...");
		SpringApplication.run(GitcolabApplication.class, args);
	}

}
