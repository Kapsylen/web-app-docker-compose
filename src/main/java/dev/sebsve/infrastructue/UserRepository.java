package dev.sebsve.infrastructue;

import dev.sebsve.infrastructue.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
}
