package br.com.gustavodiniz.projectmc.services;

import br.com.gustavodiniz.projectmc.entities.*;
import br.com.gustavodiniz.projectmc.entities.enums.CustomerType;
import br.com.gustavodiniz.projectmc.entities.enums.PaymentStatus;
import br.com.gustavodiniz.projectmc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

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

    public void instantiateTestDatabase() throws ParseException {
        Category category1 = new Category(null, "Informática");
        Category category2 = new Category(null, "Escritório");
        Category category3 = new Category(null, "Cama, Mesa e Banho");
        Category category4 = new Category(null, "Eletrônicos");
        Category category5 = new Category(null, "Jardinagem");
        Category category6 = new Category(null, "Decoração");
        Category category7 = new Category(null, "Perfumaria");

        Product product1 = new Product(null, "Computador", 2000.0);
        Product product2 = new Product(null, "Impressora", 800.0);
        Product product3 = new Product(null, "Mouse", 80.0);
        Product product4 = new Product(null, "Mesa de Escritório", 300.0);
        Product product5 = new Product(null, "Toalha", 50.0);
        Product product6 = new Product(null, "Colcha", 200.0);
        Product product7 = new Product(null, "TV true color", 1200.0);
        Product product8 = new Product(null, "Roçadeira", 80.0);
        Product product9 = new Product(null, "Abajour", 100.0);
        Product product10 = new Product(null, "Pendente", 180.0);
        Product product11 = new Product(null, "Shampoo", 90.0);

        category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
        category2.getProducts().addAll(Arrays.asList(product2, product4));
        category3.getProducts().addAll(Arrays.asList(product5, product6));
        category4.getProducts().addAll(Arrays.asList(product1, product2, product3, product7));
        category5.getProducts().addAll(Arrays.asList(product8));
        category6.getProducts().addAll(Arrays.asList(product9, product10));
        category7.getProducts().addAll(Arrays.asList(product11));

        product1.getCategories().addAll(Arrays.asList(category1, category4));
        product2.getCategories().addAll(Arrays.asList(category1, category2, category4));
        product3.getCategories().addAll(Arrays.asList(category1, category4));
        product4.getCategories().addAll(Arrays.asList(category2));
        product5.getCategories().addAll(Arrays.asList(category3));
        product6.getCategories().addAll(Arrays.asList(category3));
        product7.getCategories().addAll(Arrays.asList(category4));
        product8.getCategories().addAll(Arrays.asList(category5));
        product9.getCategories().addAll(Arrays.asList(category6, category4));
        product10.getCategories().addAll(Arrays.asList(category6));
        product11.getCategories().addAll(Arrays.asList(category7));

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4, category5, category6, category7));
        productRepository.saveAll(Arrays.asList(
                        product1, product2, product3, product4, product5,
                        product6, product7, product8, product9, product10,
                        product11
                )
        );

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
