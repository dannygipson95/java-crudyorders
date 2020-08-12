package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> findByOrderId(@PathVariable long id){
        Order order = orderService.findByOrdnum(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "/order/advanceamount", produces = "application/json")
    public ResponseEntity<?> findByAdvance(){
        List<Order> advanceOrder = orderService.getOrdersByAdvance();
        return new ResponseEntity<>(advanceOrder, HttpStatus.OK);
    }

    // http://localhost:2019/orders/order
    // Post
    // http://localhost:2019/orders/order/{id}
    // PUT
    // http://localhost:2019/orders/order/{id}
    // DELETE

}
