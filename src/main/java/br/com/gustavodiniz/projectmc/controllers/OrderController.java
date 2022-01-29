package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.entities.Order;
import br.com.gustavodiniz.projectmc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> find(@PathVariable Integer id) {

        Order order = service.find(id);
        return ResponseEntity.ok().body(order);

    }
}
