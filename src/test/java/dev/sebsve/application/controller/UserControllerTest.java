package dev.sebsve.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sebsve.application.model.UserApi;
import dev.sebsve.domain.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;


    @Test
    public void whenPostUser_thenCreateUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var inputUser = UserApi.builder()
                .userName("username")
                .password("password")
                .email("admin@mail.com")
                .build();

        var outputUser = UserApi.builder()
                .id(UUID.randomUUID().toString())
                .userName("username")
                .password("password")
                .email("admin@mail.com")
                .build();
        given(service.createUser(inputUser)).willReturn(outputUser);

        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(objectMapper.writeValueAsBytes(inputUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());

        verify(service).createUser(inputUser);
    }

    @Test
    public void givenOneUserExists_whenGetUsers_thenReturnJsonArrayWithOneUser() throws Exception {

        given(service.findAllUsers()).willReturn(List.of(UserApi.builder()
                .id(UUID.randomUUID().toString())
                .userName("username")
                .password("password")
                .email("admin@mail.com")
                .build()));

        mvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").exists());

        verify(service, VerificationModeFactory.times(1)).findAllUsers();
    }

    @Test
    public void givenUserId_whenGetUserById_thenReturnJsonUser() throws Exception {
        String userId = UUID.randomUUID().toString();
        UserApi user = UserApi.builder()
                .id(userId)
                .userName("username")
                .password("password")
                .email("admin@mail.com")
                .build();

        given(service.findUserById(userId)).willReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("admin@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"));

        verify(service, VerificationModeFactory.times(1)).findUserById(userId);
    }

    @Test
    public void givenUserId_whenDeleteUserById_thenReturnJsonUser() throws Exception {
        String userId = UUID.randomUUID().toString();
        mvc.perform(MockMvcRequestBuilders
                        .delete("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, VerificationModeFactory.times(1)).deleteUser(userId);
    }

    @Test
    public void givenUsername_whenGetUserByUsername_thenReturnJsonUser() throws Exception {
        String username = "username";
        UserApi user = UserApi.builder()
                .id(UUID.randomUUID().toString())
                .userName(username)
                .password("password")
                .email("admin@mail.com")
                .build();

        given(service.findUserByUsername(username)).willReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/userbyname/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());

        verify(service, VerificationModeFactory.times(1)).findUserByUsername(username);
    }

    @Test
    public void givenPassword_whenGetUserByEmail_thenReturnJsonUser() throws Exception {
        String email = "admin@mail.com";
        UserApi user = UserApi.builder()
                .id(UUID.randomUUID().toString())
                .userName("username")
                .password("password")
                .email(email)
                .build();

        given(service.findUserByEmail(email)).willReturn(user);

        mvc.perform(MockMvcRequestBuilders
                        .get("/userbyemail/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());

        verify(service, VerificationModeFactory.times(1)).findUserByEmail(email);
    }

    @Test
    public void givenUserId_whenUpdateUser_thenReturnJsonUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userId = UUID.randomUUID().toString();
        UserApi newUser = UserApi.builder()
                .id(userId)
                .userName("newUsername")
                .password("newPassword")
                .email("newEmail@mail.com")
                .build();

        given(service.updateUser(userId, newUser)).willReturn(newUser);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/user/{id}", userId)
                        .content(objectMapper.writeValueAsBytes(newUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("newUsername"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newEmail@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("newPassword"));

        verify(service, VerificationModeFactory.times(1)).updateUser(userId, newUser);
    }
}