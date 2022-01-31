package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.dto.ClientDTO;
import br.com.gustavodiniz.projectmc.dto.ClientNewDTO;
import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clientList = service.findAll();
        List<ClientDTO> clientDTOList = clientList.stream().map(ClientDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientDTOList);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
        Client client = service.fromDTO(clientNewDTO);
        client = service.insert(client);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable Integer id) {
        Client client = service.fromDTO(clientDTO);
        client.setId(id);
        client = service.update(client);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();

    }
}
