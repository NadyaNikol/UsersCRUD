package com.example.usersCRUD.repositories;

import com.example.usersCRUD.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User getUserByNameOrEmail(String name, String email);
}
