package com.xeo.ecommerce.backend.infrastructure.adapter;

import com.xeo.ecommerce.backend.domain.model.User;
import com.xeo.ecommerce.backend.domain.port.IUserRepository;
import com.xeo.ecommerce.backend.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserCrudRepositoryImpl implements IUserRepository {

    private final IUserCrudRepository iUserCrudRepository;
    private final UserMapper userMapper;

    public UserCrudRepositoryImpl(IUserCrudRepository iUserCrudRepository, UserMapper userMapper) {
        this.iUserCrudRepository = iUserCrudRepository;
        this.userMapper = userMapper;
    }


    @Override
    public User save(User user) {
        return userMapper.toUser(iUserCrudRepository.save( userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(iUserCrudRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User with email: "+email + "not found")
        ));
    }

    @Override
    public User findById(Integer id) {
        return userMapper.toUser( iUserCrudRepository.findById(id).orElse(null));
    }
}
