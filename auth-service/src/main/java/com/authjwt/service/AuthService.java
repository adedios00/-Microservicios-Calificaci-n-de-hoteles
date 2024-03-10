package com.authjwt.service;

import com.authjwt.dto.AuthUserDto;
import com.authjwt.dto.TokenDto;
import com.authjwt.entity.AuthUser;
import com.authjwt.repository.AuthUserRepository;
import com.authjwt.security.JwtProvider;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if (user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())){
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        return null;
    }

    public TokenDto validate (String token){
        if(!jwtProvider.validate(token)){
            return null;
        }
        String userName = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByUserName(userName).isPresent()){
            return null;
        }
        return new TokenDto(token);
    }

}
