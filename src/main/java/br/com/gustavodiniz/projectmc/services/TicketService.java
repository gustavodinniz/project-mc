package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.PaymentByTicket;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TicketService {

    public void fillInPaymentWithTicket(PaymentByTicket paymentByTicket, Date momentOrder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(momentOrder);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        paymentByTicket.setDueDate(calendar.getTime());
    }
}
