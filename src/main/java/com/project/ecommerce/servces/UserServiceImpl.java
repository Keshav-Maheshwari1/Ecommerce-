package com.project.ecommerce.servces;

import com.project.ecommerce.entities.User;
import com.project.ecommerce.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailExists(String email) {
        return userRepository.findAll().stream().anyMatch(
                user->user.getEmail().equals(email)
        );
    }
    @Override
    public ResponseEntity<String> add(User newUser) {
        if(!StringUtils.hasLength(newUser.getEmail()) || !StringUtils.hasLength(newUser.getUsername())
        || !StringUtils.hasLength(newUser.getPassword())){
            return new ResponseEntity<>("Required Fields Are Missing", HttpStatus.BAD_REQUEST);
        }
        if(!newUser.getEmail().contains("@")){
            return new ResponseEntity<>("Email must contain @", HttpStatus.BAD_REQUEST);
        }
        if(emailExists(newUser.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
        userRepository.save(newUser);
        return new ResponseEntity<>(newUser.getUsername(),HttpStatus.CREATED);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<String> signIn(String email,String password){
        if(!StringUtils.hasLength(email) ||  !StringUtils.hasLength(password)){
            return new ResponseEntity<>("Required Fields Are Missing", HttpStatus.BAD_REQUEST);
        }
        if(!email.contains("@")){
            return new ResponseEntity<>("Email must contain @", HttpStatus.BAD_REQUEST);
        }
        if(emailExists(email)){
            User user = getUser(email);

            if(!user.getPassword().equals(password)){
                return new ResponseEntity<>("Incorrect Password",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(user.getUsername(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Email Not Found", HttpStatus.NOT_FOUND);
    }
    @Override
    public ResponseEntity<String> updatePassword(String email,String password) {
        if(!StringUtils.hasLength(email) || !StringUtils.hasLength(password)){
            return new ResponseEntity<>("Required Fields Are Missing",HttpStatus.BAD_REQUEST);
        }
        if(emailExists(email)){
            userRepository.updatePassword(email, password);
            return new ResponseEntity<>("Email updated Successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
    @Override
    public boolean logout(String email) {
        return userRepository.deleteByEmail(email);
    }
}