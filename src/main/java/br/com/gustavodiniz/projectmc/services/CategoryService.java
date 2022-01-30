package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.dto.CategoryDTO;
import br.com.gustavodiniz.projectmc.entities.Category;
import br.com.gustavodiniz.projectmc.repositories.CategoryRepository;
import br.com.gustavodiniz.projectmc.services.exceptions.DataIntegrityException;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public Category insert(Category category) {
        category.setId(null);
        return repository.save(category);
    }

    public Category update(Category category) {
        find(category.getId());
        return repository.save(category);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Cannot delete a category that has products.");
        }

    }

    public Category fromDTO(CategoryDTO categoryDTO){
        return new Category(categoryDTO.getId(), categoryDTO.getName());
    }


}
