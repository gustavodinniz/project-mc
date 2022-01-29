package br.com.gustavodiniz.projectmc.repositories;

import br.com.gustavodiniz.projectmc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
