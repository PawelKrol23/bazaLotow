package org.example.bazalotow2.config;

import lombok.RequiredArgsConstructor;
import org.example.bazalotow2.entity.User;
import org.example.bazalotow2.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersistentUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: %s not found!".formatted(email)));

        return new UserPrincipal(
                user.getPassword(),
                user.getEmail(),
                List.of(new SimpleGrantedAuthority(user.getAuthority()))
        );
    }
}
