package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.repositories.CategoryRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category find(Integer id) {
        Optional<Category> category = repository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException(
                "Category with id " + id + " not found, type: " + Category.class.getName()));
    }

    public Category insert(Category category) {
        category.setId(null);
        return repository.save(category);
    }

    public Category update(Category category) {
        find(category.getId());
        return repository.save(category);
    }
}
