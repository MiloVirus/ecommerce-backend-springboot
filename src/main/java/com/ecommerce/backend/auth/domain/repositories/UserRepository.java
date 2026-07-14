package com.ecommerce.backend.auth.domain.repositories;
import java.util.Optional;
import java.util.UUID;

import com.ecommerce.backend.auth.domain.models.User;

public interface UserRepository {

    User saveUser(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);

}
