package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.entities.Product;
import br.com.gustavodiniz.projectmc.repositories.CategoryRepository;
import br.com.gustavodiniz.projectmc.repositories.ProductRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product find(Integer id) {
        Optional<Product> order = repository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException(
                "Product with id " + id + " not found, type: " + Product.class.getName()));
    }

    public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
        return repository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);

    }
}
