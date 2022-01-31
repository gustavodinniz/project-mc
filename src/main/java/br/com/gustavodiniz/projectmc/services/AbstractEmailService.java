package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.entities.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Order order) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromOrder(order);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Order order) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(order.getClient().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Your order has been confirmed! Code: " + order.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(order.toString());
        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(client, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject("New password request");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("New password: " + newPass);
        return sm;
    }
}
