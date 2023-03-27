package com.app.auth.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}
	
	@Override 
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
					.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			System.out.println(authenticationRequest.getUsername() + " " +  authenticationRequest.getPassword());
			System.out.println("Hello World");
			Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()); //add breakpoints here //{"principal"="linda", "credentials"="password", "authorities"="0", "details"="null", "authenticated"="false"}
			Authentication authenticate = authenticationManager.authenticate(authentication);
			return authenticate; //add breakpoints here //{"principal"="ApplicationUser={"username"="linda","password"="encoded", "grantedAuthorities"="size = 5 = {0 = "student:write", 1 = "student:read", 2 = "course:read", 3 = "ROLE_ADMIN", 4 = "course:write"}", isAccountNonExpired="true", "isCredentialsNonExpired="true", "isEnabled"="true", "}", "credentials"="null", "authorities"="5", "details"="null", "authenticated"="true"}
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override 
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenexpirationAfterDays()))) //plusWeeks
				.signWith(secretKey)
				.compact();
				//response.addHeader("Authorization", "Bearer " + token); //add break point here
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token); //add break point here
	}
}
