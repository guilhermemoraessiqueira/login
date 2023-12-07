package br.com.login.login.auth.services;

import java.sql.Date;

import br.com.login.login.security.TokenService;
import br.com.login.login.user.dtos.AutheticationDto;
import br.com.login.login.user.dtos.LoginResponseDto;
import br.com.login.login.user.dtos.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.login.login.user.repositories.UserRepository;
import br.com.login.login.user.models.UserModel;

import jakarta.validation.Valid;

@Service
public class AuthorizationService implements UserDetailsService{

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AutheticationDto data){
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    public ResponseEntity<Object> register (@RequestBody RegisterDto registerDto){
        if (this.userRepository.findByLogin(registerDto.login()) != null ) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        UserModel newUser = new UserModel(registerDto.login(), encryptedPassword, registerDto.role());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}