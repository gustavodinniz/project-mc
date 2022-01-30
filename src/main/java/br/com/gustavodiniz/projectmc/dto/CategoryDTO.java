package br.com.gustavodiniz.projectmc.dto;

import br.com.gustavodiniz.projectmc.entities.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Mandatory filling")
    @Length(min = 5, max = 80, message = "The length must be between 5 and 80 characters")
    private String name;

    public CategoryDTO() {

    }

    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
