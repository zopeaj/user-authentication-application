package com.app.auth.user;

import java.util.Optional;


public interface ApplicationUserDao {
  public Optional<ApplicationUser> selectApplicationUserByUsername(String username); 
}
