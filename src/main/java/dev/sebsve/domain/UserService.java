package dev.sebsve.domain;

import dev.sebsve.application.model.UserApi;
import dev.sebsve.infrastructue.UserRepository;
import dev.sebsve.infrastructue.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.sebsve.application.model.UserApi.toUserApi;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserApi createUser(UserApi user) {
       return toUserApi(userRepository.save(
                User.builder()
                        .username(user.userName())
                        .password(user.password())
                        .email(user.email())
                        .build())
        );
    }

    public UserApi findUserById(String id) {
        return toUserApi(userRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    public UserApi findUserByUsername(String username) {
        return toUserApi(userRepository.findByUsername(username));
    }

    public UserApi findUserByEmail(String email) {
        return toUserApi(userRepository.findByEmail(email));
    }

    public List<UserApi> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserApi::toUserApi)
                .toList();
    }

    public UserApi updateUser(String id, UserApi newUser) {
        var updatedUser =  userRepository.findById(id)
                .map(user -> User.builder()
                        .username(newUser.userName())
                        .password(newUser.password())
                        .email(newUser.email())
                        .build())
                .orElseThrow(IllegalAccessError::new);

      return toUserApi(userRepository.save(updatedUser));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
