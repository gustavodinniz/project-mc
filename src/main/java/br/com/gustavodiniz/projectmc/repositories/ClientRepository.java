package br.com.gustavodiniz.projectmc.repositories;

import br.com.gustavodiniz.projectmc.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}