package br.com.gustavodiniz.projectmc.dto;

import br.com.gustavodiniz.projectmc.entities.Client;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Mandatory filling")
    @Length(min = 5, max = 120, message = "The length must be between 5 and 120 characters")
    private String name;

    @NotEmpty(message = "Mandatory filling")
    @Email(message = "Invalid email")
    private String email;

    public ClientDTO() {

    }

    public ClientDTO(Client client) {
        id = client.getId();
        name = client.getName();
        email = client.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
