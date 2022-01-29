package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
