package dev.sebsve.application.controller;

import dev.sebsve.application.model.UserApi;
import dev.sebsve.domain.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a User", description = "Create and returns the User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserApi createUser(@RequestBody UserApi userApi) {
        return userService.createUser(userApi);
    }

    @Operation(summary = "Get a User by id", description = "Returns a User as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserApi getUser(@PathVariable String id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Get a User by email", description = "Returns a User as per the email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userbyemail/{email}")
    public UserApi getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @Operation(summary = "Get a User by username", description = "Returns a User as per the username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userbyname/{username}")
    public UserApi getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @Operation(summary = "Get all Users", description = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<UserApi> getUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Update a User", description = "Update a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/user/{id}")
    public UserApi updateUser(@PathVariable String id, @RequestBody UserApi userApi) {
        return userService.updateUser(id, userApi);
    }

    @Operation(summary = "Delete a User", description = "Delete a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The User was not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
