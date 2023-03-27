package com.app.auth.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.auth.config.security.ApplicationUserRole;
import com.app.auth.config.security.PasswordConfig;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

	 private final PasswordConfig passwordConfig;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordConfig passwordConfig) {
		this.passwordConfig = passwordConfig;
	}
	

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers().stream().filter(applicationUser -> username.equals(applicationUser.getUsername())).findFirst();
	}
	
	public List<ApplicationUser> getApplicationUsers() {
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
		  new ApplicationUser("annasmith", passwordConfig.passwordEncoder().encode("password"), ApplicationUserRole.STUDENT.getGrantedAuthorities(), true, true, true, true),
		  new ApplicationUser("linda", passwordConfig.passwordEncoder().encode("password123"), ApplicationUserRole.ADMIN.getGrantedAuthorities(), true, true, true, true),
		  new ApplicationUser("tom", passwordConfig.passwordEncoder().encode("password123"), ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(), true, true, true, true)
		);
		return applicationUsers;
	}
}
