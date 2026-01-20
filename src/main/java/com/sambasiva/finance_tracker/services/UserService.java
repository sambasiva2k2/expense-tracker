package com.sambasiva.finance_tracker.services;

import com.sambasiva.finance_tracker.exceptions.InvalidUsernameOrPasswordException;
import com.sambasiva.finance_tracker.exceptions.UserAlreadyExistsException;
import com.sambasiva.finance_tracker.models.User;
import com.sambasiva.finance_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    public String login(String username, String password) {
        User userFromDb = userRepository.findByUsername(username);
        if(userFromDb != null) {
            boolean isPasswordMatched = new BCryptPasswordEncoder(12).matches(password, userFromDb.getPassword());
            if(!isPasswordMatched) {
                throw new InvalidUsernameOrPasswordException("Invalid Username or Password Provided");
            }
            return jwtService.generateToken(userFromDb);
        }
        else {
            throw new InvalidUsernameOrPasswordException("Invalid Username or Password Provided");
        }
    }

    public User addUser(User user) {
        User checkIfExists = userRepository.findByUsername(user.getUsername());
        if(checkIfExists != null) {
            throw new UserAlreadyExistsException("User already exists :" + user.getUsername());
        }
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        return userRepository.save(user);
    }

}
