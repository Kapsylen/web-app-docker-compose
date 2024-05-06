package dev.sebsve.application.controller;

import dev.sebsve.application.model.UserApi;
import dev.sebsve.domain.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public UserApi createUser(@RequestBody UserApi userApi) {
        return userService.createUser(userApi);
    }

    @GetMapping("/getUser")
    public UserApi getUser(@RequestParam String id) {
        return userService.findUserById(id);
    }

    @GetMapping("/getUserByEmail")
    public UserApi getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/getUserByName")
    public UserApi getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/allUsers")
    public List<UserApi> getUsers() {
        return userService.findAllUsers();
    }

    @PutMapping("/updateUser")
    public UserApi updateUser(@RequestBody UserApi userApi) {
        return userService.updateUser(userApi);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
    }
}
