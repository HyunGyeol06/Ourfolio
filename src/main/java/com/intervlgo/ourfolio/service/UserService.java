package com.intervlgo.ourfolio.service;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.auth.PrincipalDetailsService;
import com.intervlgo.ourfolio.dto.UserDto;
import com.intervlgo.ourfolio.dto.UserIdPasswordDto;
import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    public ResponseEntity<UserDto> signUp(UserDto request) {
        HttpStatus status = HttpStatus.OK;

        Optional<User> optionalUser = userRepository.findByUserId(request.getUserId());
        if (optionalUser.isPresent()){
            status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(status);
        }
        User user = User.builder()
                .userId(request.getUserId())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .username(request.getUsername())
                .region(request.getRegion())
                .occupation(request.getOccupation())
                .isHavingJob(request.getIsHavingJob() != null && request.getIsHavingJob())
                .isEnabled(true)
                .build();
        userRepository.save(user);

        UserDto body = user.toDto();

        return new ResponseEntity<>(body, status);
    }

    public ResponseEntity<UserDto> signIn(UserDto request) {
        HttpStatus status = HttpStatus.OK;

        PrincipalDetails principalDetails = principalDetailsService.loadUserByUsername(request.getUserId());

        if (!passwordEncoder.matches(request.getUserPassword(), principalDetails.getPassword())){
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails.getUsername(),principalDetails.getPassword(),principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        HttpHeaders headers = new HttpHeaders();

        UserDto body = userRepository.findByUserId(request.getUserId()).get().toDto();

        return new ResponseEntity<>(body, headers, status);
    }

    public ResponseEntity<Page<UserDto>> findUsers(Pageable pageable, String username, String region, String occupation) {
        Page<User> userPage = userRepository.searchUser(pageable, username, region, occupation);

        Page<UserDto> body = userPage.map(User::toDto);

        return ResponseEntity.ok(body);
    }

    public ResponseEntity<UserDto> findUserByUserId(String userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalUser.get().toDto());
    }

    @Transactional
    public ResponseEntity<UserDto> updateUser(UserDto request, PrincipalDetails principalDetails) {
        HttpStatus status = HttpStatus.OK;

        User user = principalDetails.getUser();
        user.update(request.getUsername(), request.getRegion(), request.getOccupation(), request.getIsHavingJob());

        userRepository.save(user);

        UserDto body = user.toDto();

        return new ResponseEntity<>(body, status);
    }

    @Transactional
    public ResponseEntity<UserDto> updateIdPassword(UserIdPasswordDto request, PrincipalDetails principalDetails) {
        HttpStatus status = HttpStatus.OK;

        User user = principalDetails.getUser();
        if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
        if (userRepository.existsByUserId(request.getNewId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        user.update(request.getNewId(), passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        UserDto body = user.toDto();

        return new ResponseEntity<>(body, status);
    }
    @Transactional
    public ResponseEntity<UserDto> deactivateAccount(PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        user.deactivateAccount();

        UserDto body = user.toDto();

        return ResponseEntity.ok(body);
    }

}
