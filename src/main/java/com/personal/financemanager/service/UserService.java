package com.personal.financemanager.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.personal.financemanager.dtos.UserRequest;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.repository.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public User createUser(UserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return userRepo.save(user);
    }

    public Optional<User> getUserDetailsById(Long id){
        return userRepo.findById(id);
    }

    public User updateUser(Long id,UserRequest request){
        User existingUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found!"));

        existingUser.setUsername(request.getUsername());
        existingUser.setEmail(request.getEmail());
        return userRepo.save(existingUser);
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }
}
