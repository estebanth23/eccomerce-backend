package com.xeo.ecommerce.backend.infrastructure.adapter;

import com.xeo.ecommerce.backend.infrastructure.entity.OrderEntity;
import com.xeo.ecommerce.backend.infrastructure.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IOrderCrudRepository extends CrudRepository<OrderEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o SET o.orderState = :state WHERE o.id = :id")
    void updateStateById(Integer id, String state);

    Iterable<OrderEntity> findByUserEntity(UserEntity userEntity);
}
