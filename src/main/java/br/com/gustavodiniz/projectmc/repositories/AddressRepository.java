package br.com.gustavodiniz.projectmc.repositories;

import br.com.gustavodiniz.projectmc.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
