package com.cms.security.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cms.domain.User;
import com.cms.security.domain.SysUser;
import com.cms.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // 获取User数据库中对象
    User user = userService.findByUserName(username);

    if (user == null) {
    	throw new BadCredentialsException("账号不存在");
    } else if (1 == user.getStatus()){
        throw new BadCredentialsException("账号已被锁定");
    } else {
      return new SysUser(user.getName(), username, user.getPassword(),
          true, true, true, true, mapToGrantedAuthorities(Arrays.asList(user.getUserAuthority())));
    }
  }

  /**
   * 整合Roles
   * 
   * @param authorities
   * @return
   */
  private static List<GrantedAuthority> mapToGrantedAuthorities(List<Integer> authorities) {
    List<GrantedAuthority> grants = new ArrayList<GrantedAuthority>();
    if(null != authorities && authorities.size()>0){
      for(int authoritie: authorities){
        if(0 == authoritie){
          grants.add(new SimpleGrantedAuthority("ADMIN"));
        }else{
          grants.add(new SimpleGrantedAuthority("USER"));
        }
      }
    }
    return grants;
  }
}
