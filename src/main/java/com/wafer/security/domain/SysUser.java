package com.wafer.security.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class SysUser implements UserDetails {

  private static final long serialVersionUID = 1L;

  private long userId;
  private String name;
  private String password;
  private String username;
  private Set<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  public SysUser(long userId, String name, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this(userId, name, username, password, true, true, true, true, authorities);
  }

  public SysUser(long userId, String name, String username, String password, boolean enabled,
      boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {

    if (((username == null) || "".equals(username)) || (password == null)) {
      throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
    }
    this.userId = userId;
    this.name = name;
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.credentialsNonExpired = credentialsNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
  }

  public long getUserId() {
    return userId;
  }
  
  public String getName() {
    return name;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void eraseCredentials() {
    password = null;
  }

  private static SortedSet<GrantedAuthority> sortAuthorities(
      Collection<? extends GrantedAuthority> authorities) {
    Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
    // Ensure array iteration order is predictable (as per
    // UserDetails.getAuthorities() contract and SEC-717)
    SortedSet<GrantedAuthority> sortedAuthorities =
        new TreeSet<GrantedAuthority>(new AuthorityComparator());

    for (GrantedAuthority grantedAuthority : authorities) {
      Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
      sortedAuthorities.add(grantedAuthority);
    }

    return sortedAuthorities;
  }

  private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public int compare(GrantedAuthority g1, GrantedAuthority g2) {
      // Neither should ever be null as each entry is checked before adding it to
      // the set.
      // If the authority is null, it is a custom authority and should precede
      // others.
      if (g2.getAuthority() == null) {
        return -1;
      }

      if (g1.getAuthority() == null) {
        return 1;
      }

      return g1.getAuthority().compareTo(g2.getAuthority());
    }
  }


  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof SysUser) {
      return username.equals(((SysUser) rhs).username);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }

}
