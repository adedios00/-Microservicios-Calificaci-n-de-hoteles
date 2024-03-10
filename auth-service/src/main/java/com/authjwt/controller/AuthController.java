package com.authjwt.controller;

import com.authjwt.dto.AuthUserDto;
import com.authjwt.dto.TokenDto;
import com.authjwt.entity.AuthUser;
import com.authjwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authuUserDto){
        TokenDto tokenDto = authService.login(authuUserDto);
        if(Objects.isNull(authuUserDto)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate (@RequestParam String token){
        TokenDto tokenDto = authService.validate(token);
        if(Objects.isNull(tokenDto)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDto authUserDto){
        AuthUser authUser = authService.save(authUserDto);
        if(Objects.isNull(authUser)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authUser);
    }
}
