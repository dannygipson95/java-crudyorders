package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder){
        newOrder.setOrdnum(0);
        newOrder = orderService.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newOrder.getOrdnum())
                .build()
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // http://localhost:2019/orders/order/{id}
    // PUT
    // http://localhost:2019/orders/order/{id}
    // DELETE

}
