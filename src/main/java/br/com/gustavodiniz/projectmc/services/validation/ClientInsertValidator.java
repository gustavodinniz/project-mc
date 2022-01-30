package br.com.gustavodiniz.projectmc.services.validation;

import br.com.gustavodiniz.projectmc.controllers.exceptions.FieldMessage;
import br.com.gustavodiniz.projectmc.dto.ClientNewDTO;
import br.com.gustavodiniz.projectmc.entities.enums.CustomerType;
import br.com.gustavodiniz.projectmc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
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

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
