package com.project.ecommerce.servces;

import com.project.ecommerce.entities.User;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface UserService {
    ResponseEntity<String> add(User newUser);
    List<User> getAllUsers();
    User getUser(String email);
    ResponseEntity<String> updatePassword(String email,String password);
    boolean logout(String email);
    ResponseEntity<String> signIn(String email, String password);
}
