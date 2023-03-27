package com.app.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.auth.config.security.ApplicationUserPermission;
import com.app.auth.config.security.ApplicationUserRole;

@SpringBootApplication
public class UserAuthenticationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationAppApplication.class, args);
	}
}
