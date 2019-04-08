package com.enterprise.usermgmt.users;

import com.enterprise.usermgmt.users.model.Status;
import com.enterprise.usermgmt.users.model.User;
import com.enterprise.usermgmt.exceptions.NotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enterprise/users")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/listUsers")
    public List<User> getAllUsers() {
        List<User> users = this.usersRepository.findAll();
        return CollectionUtils.isNotEmpty(users) ? this.filterInactiveUsers(users) : new ArrayList<>();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long userId) {
        return ResponseEntity.ok(this.usersRepository.findById(userId).get());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws NotFoundException {
        User user = this.usersRepository.findById(userId).get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setDateOfBirth(userDetails.getDateOfBirth());

        return ResponseEntity.ok(this.usersRepository.save(user));
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> createUser( @RequestBody User userDetails) {
        userDetails.setStatus(Status.ACTIVE.getStatus());
        return ResponseEntity.ok(this.usersRepository.save(userDetails));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) throws NotFoundException {
        User user = this.usersRepository.findById(userId).get();
        user.setStatus(Status.INACTIVE.name());

        return ResponseEntity.ok(this.usersRepository.save(user));
    }

    private List<User> filterInactiveUsers(List<User> users) {
        return users.stream()
                .filter(user -> !user.getStatus().equalsIgnoreCase(Status.INACTIVE.getStatus()))
                .collect(Collectors.toList());
    }
}
