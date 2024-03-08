package com.project.ecommerce.controllers;

import com.project.ecommerce.entities.LoggedInUser;
import com.project.ecommerce.entities.User;
import com.project.ecommerce.servces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Generating All registered users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    // Finding user by email
    @GetMapping("/user/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
        User user = userService.getUser(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Sign In controller
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody LoggedInUser user){
        return userService.signIn(user.getEmail(), user.getPassword());
    }
    // Adding new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        return userService.add(user);
    }
    // Updating a password
    @PostMapping("/reset")
    public ResponseEntity<String> updatePassword(@RequestBody LoggedInUser user){
        System.out.println("Updating password");
        return userService.updatePassword(user.getEmail(), user.getPassword());
    }

    // deleting a userByEmail means logOut
    @DeleteMapping("/logout/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email){
        return userService.logout(email);
    }



}
