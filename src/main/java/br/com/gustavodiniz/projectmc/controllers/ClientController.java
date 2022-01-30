package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.dto.ClientDTO;
import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> find(@PathVariable Integer id) {

        Client Client = service.find(id);
        return ResponseEntity.ok().body(Client);

    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clientList = service.findAll();
        List<ClientDTO> clientDTOList = clientList.stream().map(ClientDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientDTOList);

    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Client> clientList = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClientDTO> clientDTOList = clientList.map(ClientDTO::new);
        return ResponseEntity.ok().body(clientDTOList);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Integer id) {
        Client client = service.fromDTO(clientDTO);
        client.setId(id);
        client = service.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();

    }
}
