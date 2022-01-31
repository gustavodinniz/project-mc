package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.Client;
import br.com.gustavodiniz.projectmc.entities.Order;
import br.com.gustavodiniz.projectmc.entities.OrderedItem;
import br.com.gustavodiniz.projectmc.entities.PaymentByTicket;
import br.com.gustavodiniz.projectmc.entities.enums.PaymentStatus;
import br.com.gustavodiniz.projectmc.repositories.OrderRepository;
import br.com.gustavodiniz.projectmc.repositories.OrderedItemRepository;
import br.com.gustavodiniz.projectmc.repositories.PaymentRepository;
import br.com.gustavodiniz.projectmc.security.UserSS;
import br.com.gustavodiniz.projectmc.services.exceptions.AuthorizationException;
import br.com.gustavodiniz.projectmc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    private EmailService emailService;

    public Order find(Integer id) {
        Optional<Order> order = repository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException(
                "Order with id " + id + " not found, type: " + Order.class.getName()));
    }

    @Transactional
    public Order insert(Order order) {
        order.setId(null);
        order.setInstant(new Date());
        order.setClient(clientService.find(order.getClient().getId()));
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setOrder(order);

        if (order.getPayment() instanceof PaymentByTicket) {
            PaymentByTicket paymentByTicket = (PaymentByTicket) order.getPayment();
            ticketService.fillInPaymentWithTicket(paymentByTicket, order.getInstant());
        }

        order = repository.save(order);
        paymentRepository.save(order.getPayment());

        for (OrderedItem orderedItem : order.getItems()) {
            orderedItem.setDiscount(0.00);
            orderedItem.setProduct(productService.find(orderedItem.getProduct().getId()));
            orderedItem.setPrice(orderedItem.getProduct().getPrice());
            orderedItem.setOrder(order);
        }

        orderedItemRepository.saveAll(order.getItems());
        emailService.sendOrderConfirmationEmail(order);
        return order;
    }

    public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Access Denied");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Client client = clientService.find(user.getId());
        return repository.findByClient(client, pageRequest);
    }
}
