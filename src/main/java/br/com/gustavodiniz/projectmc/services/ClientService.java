package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.repositories.ClientRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client find(Integer id) {
        Optional<Client> client = repository.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException(
                "Client with id " + id + " not found, type: " + Client.class.getName()));
    }
}
