package br.com.gustavodiniz.projectmc.entities;

import br.com.gustavodiniz.projectmc.entities.enums.PaymentStatus;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_payment_by_card")
public class PaymentByCard extends Payment {

    private static final long serialVersionUID = 1L;

    private Integer numberOfInstallments;

    public PaymentByCard() {

    }

    public PaymentByCard(Integer id, PaymentStatus status, Order order, Integer numberOfInstallments) {
        super(id, status, order);
        this.numberOfInstallments = numberOfInstallments;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }


}
