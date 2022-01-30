package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.dto.CategoryDTO;
import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> categoryList = service.findAll();
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(CategoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDTOList);

    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Category category) {
        category = service.insert(category);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable Integer id) {
        category.setId(id);
        category = service.update(category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();

    }
}
