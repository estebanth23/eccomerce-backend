package com.xeo.ecommerce.backend.domain.port;

import com.xeo.ecommerce.backend.domain.model.User;

public interface IUserRepository {
    User save(User user);
    User findByEmail(String email);
    User findById(Integer id);
}
