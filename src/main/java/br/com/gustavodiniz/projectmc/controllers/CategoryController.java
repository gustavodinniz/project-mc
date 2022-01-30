package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.dto.CategoryDTO;
import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Category> categoryList = service.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoryDTO> categoryDTOList = categoryList.map(CategoryDTO::new);
        return ResponseEntity.ok().body(categoryDTOList);

    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = service.fromDTO(categoryDTO);
        category = service.insert(category);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id) {
        Category category = service.fromDTO(categoryDTO);
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
