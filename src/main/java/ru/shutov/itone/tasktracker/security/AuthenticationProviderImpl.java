package ru.shutov.itone.tasktracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.shutov.itone.tasktracker.entity.User;
import ru.shutov.itone.tasktracker.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String credentials = passwordEncoder.encode(authentication.getCredentials().toString());

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Cannot find user with username " + username));

        if (!credentials.equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect credentials");
        }

        UserDetails principal = new UserDetailsImpl(user);

        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
