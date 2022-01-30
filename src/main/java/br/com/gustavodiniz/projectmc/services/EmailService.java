package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Order;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Order order);

    void sendEmail(SimpleMailMessage message);
}
