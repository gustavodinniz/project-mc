package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.controllers.utils.URL;
import br.com.gustavodiniz.projectmc.dto.ProductDTO;
import br.com.gustavodiniz.projectmc.entities.Product;
import br.com.gustavodiniz.projectmc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> find(@PathVariable Integer id) {

        Product product = service.find(id);
        return ResponseEntity.ok().body(product);

    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = URL.decodeParam(name);
        List<Integer> ids = URL.decodeIntList(categories);
        Page<Product> productPage = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProductDTO> productDTOPage = productPage.map(ProductDTO::new);
        return ResponseEntity.ok().body(productDTOPage);

    }
}
