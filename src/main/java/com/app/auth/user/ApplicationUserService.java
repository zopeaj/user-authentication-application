package com.app.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class ApplicationUserService implements UserDetailsService{
	
	@Autowired
	@Qualifier("fake") ApplicationUserDao applicationUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return applicationUserDao.selectApplicationUserByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
	}
}
