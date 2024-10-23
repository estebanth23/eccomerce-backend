package com.xeo.ecommerce.backend.infrastructure.adapter;

import com.xeo.ecommerce.backend.domain.model.Order;
import com.xeo.ecommerce.backend.domain.model.OrderState;
import com.xeo.ecommerce.backend.domain.port.IOrderRepository;
import com.xeo.ecommerce.backend.infrastructure.entity.OrderEntity;
import com.xeo.ecommerce.backend.infrastructure.entity.UserEntity;
import com.xeo.ecommerce.backend.infrastructure.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderCrudRepositoryImpl implements IOrderRepository {

    private final OrderMapper orderMapper;
    private final IOrderCrudRepository iOrderCrudRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toOrderEntity(order);
        orderEntity.getOrderProducts().forEach(
                orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );

        return orderMapper.toOrder(iOrderCrudRepository.save(orderEntity));
    }

    @Override
    public Order findById(Integer id) {
        return orderMapper.toOrder( iOrderCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No existe el " +id+ "no se encuentra")
        ));
    }

    @Override
    public Iterable<Order> findAll() {
        return orderMapper.toOrderList( iOrderCrudRepository.findAll());
    }

    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        return orderMapper.toOrderList( iOrderCrudRepository.findByUserEntity(userEntity));
    }

    @Override
    public void updateStateById(Integer id, String state) {
        if(state.equals(OrderState.CANCEL)){
            iOrderCrudRepository.updateStateById(id, String.valueOf(OrderState.CANCEL));
        }else{
            iOrderCrudRepository.updateStateById(id, String.valueOf(OrderState.CONFIRM));
        }
    }
}
