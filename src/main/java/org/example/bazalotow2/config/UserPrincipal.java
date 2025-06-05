package org.example.bazalotow2.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private String password;
    private String username;
    private List<SimpleGrantedAuthority> authorities;
}
