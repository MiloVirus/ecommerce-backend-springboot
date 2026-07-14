package com.ecommerce.backend.auth.domain.models;

import java.util.UUID;



public record User(
    UUID id,
    String email,
    String password,
    String firstName,
    String lastName,
    Role role

) {

}
