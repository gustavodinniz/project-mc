package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.dto.ClientDTO;
import br.com.gustavodiniz.projectmc.dto.ClientNewDTO;
import br.com.gustavodiniz.projectmc.entities.Address;
import br.com.gustavodiniz.projectmc.entities.City;
import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.entities.enums.CustomerType;
import br.com.gustavodiniz.projectmc.repositories.AddressRepository;
import br.com.gustavodiniz.projectmc.repositories.ClientRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.DataIntegrityException;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public Client find(Integer id) {
        Optional<Client> client = repository.findById(id);
        return client.orElseThrow(() -> new ObjectNotFoundException(
                "Client with id " + id + " not found, type: " + Client.class.getName()));
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Client insert(Client client) {
        client.setId(null);
        client = repository.save(client);
        addressRepository.saveAll(client.getAddresses());
        return client;
    }

    public Client update(Client client) {
        Client entity = find(client.getId());
        updateData(entity, client);
        return repository.save(entity);
    }

    private void updateData(Client entity, Client client) {
        entity.setName(client.getName());
        entity.setEmail(client.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Cannot delete a client that has orders.");
        }

    }

    public Client fromDTO(ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null);
    }

    public Client fromDTO(ClientNewDTO clientNewDTO) {
        Client client = new Client(
                null, clientNewDTO.getName(),
                clientNewDTO.getEmail(),
                clientNewDTO.getCpfOrCnpj(),
                CustomerType.toEnum(clientNewDTO.getType())
        );

        City city = new City(clientNewDTO.getCityId(), null, null);

        Address address = new Address(
                null,
                clientNewDTO.getPublicPlace(),
                clientNewDTO.getNumber(),
                clientNewDTO.getComplement(),
                clientNewDTO.getDistrict(),
                clientNewDTO.getCep(),
                client,
                city
        );

        client.getAddresses().add(address);
        client.getPhones().add(clientNewDTO.getPhone1());

        if (clientNewDTO.getPhone2() != null) {
            client.getPhones().add(clientNewDTO.getPhone2());
        }

        if (clientNewDTO.getPhone3() != null) {
            client.getPhones().add(clientNewDTO.getPhone3());
        }

        return client;
    }
}
