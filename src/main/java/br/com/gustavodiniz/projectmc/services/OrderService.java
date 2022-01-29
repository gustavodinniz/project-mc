package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Order;
import br.com.gustavodiniz.projectmc.repositories.OrderRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order find(Integer id) {
        Optional<Order> order = repository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException(
                "Order with id " + id + " not found, type: " + Order.class.getName()));
    }
}
