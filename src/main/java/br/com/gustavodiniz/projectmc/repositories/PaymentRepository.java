package br.com.gustavodiniz.projectmc.repositories;

import br.com.gustavodiniz.projectmc.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
