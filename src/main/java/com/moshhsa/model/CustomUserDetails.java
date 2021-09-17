package com.moshhsa.model;

import com.moshhsa.entites.Permission;
import com.moshhsa.entites.Role;
import com.moshhsa.entites.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> roleAndPermissions = new HashSet<>();
        Set<Role> roles = user.getRoles();

        for (Role role : roles)
        {
            roleAndPermissions.add(role.getName());
			List<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions)
			{
				roleAndPermissions.add("ROLE_"+permission.getName());
			}
        }
        String[] roleNames = new String[roleAndPermissions.size()];
        return AuthorityUtils.createAuthorityList(roleAndPermissions.toArray(roleNames));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}