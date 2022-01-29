package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> find(@PathVariable Integer id) {

        Category category = service.find(id);
        return ResponseEntity.ok().body(category);

    }
}
