package com.br.luccasdev.projectSpringSecurity.controller;

import com.br.luccasdev.projectSpringSecurity.controller.dto.CreateUserDto;
import com.br.luccasdev.projectSpringSecurity.model.Role;
import com.br.luccasdev.projectSpringSecurity.model.User;
import com.br.luccasdev.projectSpringSecurity.repository.RoleRepository;
import com.br.luccasdev.projectSpringSecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto){

        // verificando se ja existe o username no banco de dados
        var userFromDb = userRepository.findByUsername(userDto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRoles(Set.of(roleRepository.findByName(Role.Values.BASIC.name())));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')") // fazendo com que somente usuarios ADMIN usem esse endpoint
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }


}
