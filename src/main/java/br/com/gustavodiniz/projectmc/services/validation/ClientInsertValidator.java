package br.com.gustavodiniz.projectmc.services.validation;

import br.com.gustavodiniz.projectmc.controllers.exceptions.FieldMessage;
import br.com.gustavodiniz.projectmc.dto.ClientNewDTO;
import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.entities.enums.CustomerType;
import br.com.gustavodiniz.projectmc.repositories.ClientRepository;
import br.com.gustavodiniz.projectmc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    @Autowired
    private ClientRepository repository;

    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getType().equals(CustomerType.PHYSICAL_PERSON.getCode()) && !BR.isValidCPF(objDto.getCpfOrCnpj())) {
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CPF"));
        }

        if (objDto.getType().equals(CustomerType.LEGAL_PERSON.getCode()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
            list.add(new FieldMessage("cpfOrCnpj", "Invalid CNPJ"));
        }

        Client aux = repository.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Already existing email"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
