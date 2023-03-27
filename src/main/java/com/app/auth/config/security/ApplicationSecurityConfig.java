package com.app.auth.config.security;

import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.RememberMeServices;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
//import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.app.auth.jwt.JwtConfig;
import com.app.auth.jwt.JwtTokenVerifier;
import com.app.auth.jwt.JwtUsernameAndPasswordAuthenticationFilter;
//import com.app.auth.user.ApplicationUserDao;
import com.app.auth.user.ApplicationUserService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import static org.springframework.security.config.Customizer.withDefaults;
import static com.app.auth.config.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {
	
	private final PasswordEncoder passwordEncoder;
	private final PasswordConfig passwordEncoder2;
	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;
	//AuthenticationProvider authenticationProvider;
	AuthenticationManager authenticationManager;
	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, PasswordConfig passwordEncoder2, SecretKey secretKey, JwtConfig jwtConfig) {
		this.passwordEncoder = passwordEncoder;
		this.passwordEncoder2 = passwordEncoder2;
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}
	 	
 
 	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
 	     http
	 		.csrf().disable()
	 		.sessionManagement()
 			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
 		.and()
	 		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager, jwtConfig, secretKey)) 	
	 		.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
	 		.authorizeHttpRequests()
	 		.requestMatchers("/", "index", "/css/*", "/js/*").permitAll()
	 		.requestMatchers("/api/**").hasRole(STUDENT.name())
	 		.anyRequest()
	 		.authenticated();
 		return http.build();
 	}
 	 	
 	public void configure(AuthenticationManagerBuilder auth) throws Exception {
 		auth.authenticationProvider(doaAuthenticationProvider());
 	}
 	
 	
 	@Bean 
 	public DaoAuthenticationProvider doaAuthenticationProvider() {
 		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
 		provider.setPasswordEncoder(passwordEncoder2.passwordEncoder());
 		provider.setUserDetailsService(userDetailsService());
 		return provider;
 	}
 	
 	  @Bean 
 	  protected UserDetailsService userDetailsService(){
 	    UserDetails annaSmithUser = User.builder()
 		.username("annasmith")
 		.password(passwordEncoder2.passwordEncoder().encode("password")) //break points
 		.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT roles("STUDENT")
 		.authorities(STUDENT.getGrantedAuthorities())
 		.build();
 	    
 	    UserDetails lindaUser = User.builder()
 		.username("linda")
 		.password(passwordEncoder2.passwordEncoder().encode("password123"))
 		.roles(ApplicationUserRole.ADMIN.name()) //roles("ADMIN") ROLE_ADMIN
 		.authorities(ADMIN.getGrantedAuthorities())
 		.build();

 	    UserDetails tomUser = User.builder()
 		.username("tom")
 		.password(passwordEncoder2.passwordEncoder().encode("password123"))
 		.roles(ApplicationUserRole.ADMINTRAINEE.name()) //roles("ADMINTRAINEE") ROLE_ADMINTRAINEE
 		.authorities(ADMINTRAINEE.getGrantedAuthorities())
 		.build(); //restart the server
 	    
 	    return new InMemoryUserDetailsManager( //add break points to the this
	 	    annaSmithUser,
	 		lindaUser,
	 		tomUser
 	    );	
 	  }
 	
 	@Bean 
 	public ApplicationUserService applicationUserService() {
 		return new ApplicationUserService();
 	}

}