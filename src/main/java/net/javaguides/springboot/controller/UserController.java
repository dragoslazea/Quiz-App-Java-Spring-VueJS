package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/usertable")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id: " + id));
        return ResponseEntity.ok(user);
    }

    @GetMapping("getUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("Incorrect username or password: " + username));
        return ResponseEntity.ok(user);
    }

    @PostMapping("username")
    public ResponseEntity<User> getUserByUsernameAndPassword(@RequestBody User thisUser){
        User user = userRepository.findByUsername(thisUser.getUsername()).filter(x -> x.getPassword().equals(thisUser.getPassword())).
                orElseThrow(() -> new ResourceNotFoundException("Incorrect username or password: " + thisUser.getUsername()));
        return ResponseEntity.ok(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id: " + id));

        updateUser.setUsername(userDetails.getUsername());
        updateUser.setPassword(userDetails.getPassword());

        userRepository.save(updateUser);

        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id: " + id));

        userRepository.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
