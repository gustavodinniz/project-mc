package br.com.gustavodiniz.projectmc.controllers;

import br.com.gustavodiniz.projectmc.entities.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> list() {

        Category category = new Category(1, "Informática");
        Category category2 = new Category(2, "Escritório");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category2);
        return categoryList;
    }
}
