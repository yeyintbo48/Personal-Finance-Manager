package com.personal.financemanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.personal.financemanager.dtos.UserRequest;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity <User> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserDetailsById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserDetailsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity <User> updateUser(@PathVariable Long id,@RequestBody @Valid UserRequest request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
