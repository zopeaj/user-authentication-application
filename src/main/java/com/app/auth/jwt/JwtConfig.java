package com.app.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.SignatureAlgorithm;

@ConfigurationProperties(prefix = "application.jwt")
@Configuration
public class JwtConfig {
	private String secretKey;
	private String tokenPrefix;
	private int tokenexpirationAfterDays;
	
	public JwtConfig() {
		
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public int getTokenexpirationAfterDays() {
		return tokenexpirationAfterDays;
	}

	public void setTokenexpirationAfterDays(int tokenexpirationAfterDays) {
		this.tokenexpirationAfterDays = tokenexpirationAfterDays;
	}
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION; 
	}
}
