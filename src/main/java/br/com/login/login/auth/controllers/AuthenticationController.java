package br.com.login.login.auth.controllers;

import br.com.login.login.auth.services.AuthorizationService;
import br.com.login.login.user.dtos.AutheticationDto;
import br.com.login.login.user.dtos.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AutheticationDto autheticationDto){
        return authorizationService.login(autheticationDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto){
        return authorizationService.register(registerDto);
    }

}
