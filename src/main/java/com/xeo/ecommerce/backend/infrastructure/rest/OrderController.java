package com.xeo.ecommerce.backend.infrastructure.rest;

import com.xeo.ecommerce.backend.application.OrderService;
import com.xeo.ecommerce.backend.domain.model.Order;
import com.xeo.ecommerce.backend.domain.model.OrderState;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order){
        if(order.getOrderState().toString().equals(OrderState.CANCEL.toString())){
            order.setOrderState(OrderState.CANCEL);
        }else {
            order.setOrderState(OrderState.CONFIRM);
        }
        return ResponseEntity.ok(orderService.save(order));
    }

    @PostMapping("/update/state/order")
    public ResponseEntity updateStateById(@PathParam("id") Integer id, @PathParam("state") String state){
       orderService.updateStateById(id, state);
       return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Order>> findByUserId(@PathVariable("id") Integer userId){
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

}
