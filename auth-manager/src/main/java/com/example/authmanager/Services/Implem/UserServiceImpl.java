package com.example.authmanager.Services.Implem;

import com.example.authmanager.Services.AuthenticationService;
import com.example.authmanager.Services.UserService;
import com.example.authmanager.User.UserPrincipal;
import com.example.authmanager.client.UserManagerClient;
import com.example.authmanager.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserManagerClient userManagerClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserResponse user = userManagerClient.getByEmail(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found");
                }
                String encodedPassword = userManagerClient.getEncodedPassword(username);
                if (encodedPassword == null) {
                    throw new UsernameNotFoundException("User password not found");
                }
                return new UserPrincipal(
                        user.email(),
                        encodedPassword,
                        user.role()
                );
            }
        };
    }
}
