package com.ecommerce.backend.auth.infrastructure.adapters;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ecommerce.backend.auth.domain.models.User;
import com.ecommerce.backend.auth.domain.repositories.UserRepository;
import com.ecommerce.backend.auth.infrastructure.persistence.JpaUserRepository;
import com.ecommerce.backend.auth.infrastructure.persistence.UserEntity;

@Component
public class UserRepositoryAdapter implements UserRepository {


    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository)
    {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User saveUser(User user)
    {
        UserEntity entity = UserEntity.fromDomain(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override 
    public Optional<User> findById(UUID id)
    {
        return jpaUserRepository.findById(id)
        .map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return jpaUserRepository.findByEmail(email)
        .map(UserEntity::toDomain);
    }
}
