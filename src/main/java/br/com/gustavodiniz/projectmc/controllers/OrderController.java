package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.dto.CategoryDTO;
import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.entities.Order;
import br.com.gustavodiniz.projectmc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Order order) {
        order = service.insert(order);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
