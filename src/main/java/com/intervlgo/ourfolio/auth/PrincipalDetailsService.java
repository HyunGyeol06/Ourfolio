package com.intervlgo.ourfolio.auth;


import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser =  userRepository.findByUserId(username);
        User user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return null;
        }

        return new PrincipalDetails(user);
    }

}

