package br.com.gustavodiniz.projectmc;

import br.com.gustavodiniz.projectmc.entities.*;
import br.com.gustavodiniz.projectmc.entities.enums.CustomerType;
import br.com.gustavodiniz.projectmc.entities.enums.PaymentStatus;
import br.com.gustavodiniz.projectmc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjectMcApplication implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectMcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category category1 = new Category(null, "Informática");
        Category category2 = new Category(null, "Escritório");

        Product product1 = new Product(null, "Computador", 2000.0);
        Product product2 = new Product(null, "Impressora", 800.0);
        Product product3 = new Product(null, "Mouse", 80.0);

        category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
        category2.getProducts().addAll(List.of(product2));

        product1.getCategories().addAll(Arrays.asList(category1));
        product2.getCategories().addAll(Arrays.asList(category1, category2));
        product3.getCategories().addAll(Arrays.asList(category1));

        categoryRepository.saveAll(Arrays.asList(category1, category2));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));

        State state1 = new State(null, "Minas Gerais");
        State state2 = new State(null, "São Paulo");

        City city1 = new City(null, "Uberlândia", state1);
        City city2 = new City(null, "São Paulo", state2);
        City city3 = new City(null, "Campinas", state2);

        state1.getCities().addAll(Arrays.asList(city1));
        state2.getCities().addAll(Arrays.asList(city2, city3));

        stateRepository.saveAll(Arrays.asList(state1, state2));
        cityRepository.saveAll(Arrays.asList(city1, city2, city3));

        Client client1 = new Client(null, "Maria Silva", "maria@hotmail.com", "36378912377", CustomerType.PHYSICAL_PERSON);
        client1.getPhones().addAll(Arrays.asList("27363323", "93838393"));
        Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", client1, city1);
        Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", client1, city2);
        client1.getAddresses().addAll(Arrays.asList(address1, address2));

        clientRepository.saveAll(Arrays.asList(client1));
        addressRepository.saveAll(Arrays.asList(address1, address2));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Order order1 = new Order(null, simpleDateFormat.parse("30/09/2017 10:32"), client1, address1);
        Order order2 = new Order(null, simpleDateFormat.parse("10/10/2017 19:32"), client1, address2);

        Payment payment1 = new PaymentByCard(null, PaymentStatus.SETTLED, order1, 6);
        order1.setPayment(payment1);

        Payment payment2 = new PaymentByTicket(null, PaymentStatus.PENDING, order2, simpleDateFormat.parse("20/10/2017 00:00"), null);
        order2.setPayment(payment2);

        client1.getOrders().addAll(Arrays.asList(order1, order2));

        orderRepository.saveAll(Arrays.asList(order1, order2));
        paymentRepository.saveAll(Arrays.asList(payment1, payment2));

        OrderedItem orderedItem1 = new OrderedItem(order1, product1, 0.00, 1, 2000.00);
        OrderedItem orderedItem2 = new OrderedItem(order1, product3, 0.00, 2, 80.00);
        OrderedItem orderedItem3 = new OrderedItem(order2, product2, 100.00, 1, 800.00);

        order1.getItems().addAll(Arrays.asList(orderedItem1, orderedItem2));
        order2.getItems().addAll(Arrays.asList(orderedItem3));

        product1.getItems().addAll(Arrays.asList(orderedItem1));
        product2.getItems().addAll(Arrays.asList(orderedItem3));
        product3.getItems().addAll(Arrays.asList(orderedItem2));

        orderedItemRepository.saveAll(Arrays.asList(orderedItem1, orderedItem2, orderedItem3));

    }
}
