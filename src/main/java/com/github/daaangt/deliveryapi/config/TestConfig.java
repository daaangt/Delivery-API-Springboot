package com.github.daaangt.deliveryapi.config;

import com.github.daaangt.deliveryapi.entities.*;

import com.github.daaangt.deliveryapi.entities.enums.OrderStatus;

import com.github.daaangt.deliveryapi.repositories.OrderItemRepository;
import com.github.daaangt.deliveryapi.repositories.OrderRepository;
import com.github.daaangt.deliveryapi.repositories.ProductRepository;
import com.github.daaangt.deliveryapi.repositories.UserRepository;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.boot.CommandLineRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            /**
                Seeding the Development Account and the Products (with Faker)
            */
            Faker faker = new Faker();

            ArrayList<Product> products = new ArrayList<>();

            User user = new User(null, "Development Account", "admin@admin.org", "123456admin", faker.address().streetAddress());

            for(int p = 0; p < 10; p++) {
                products.add(new Product(null, faker.food().ingredient(), (10 + (new Random().nextDouble() * (100 - 10))), 15));
            }

            productRepository.saveAll(products);
            userRepository.save(user);

            /**
                Seeding the Orders of the Development Account
            */
            ArrayList<Order> orders = new ArrayList<>();

            orders.add(new Order(null, user, OrderStatus.PENDING_PAYMENT, Instant.now()));
            orders.add(new Order(null, user, OrderStatus.PENDING_PAYMENT, Instant.now()));
            orders.add(new Order(null, user, OrderStatus.PENDING_PAYMENT, Instant.now()));
            orders.add(new Order(null, user, OrderStatus.PAID, Instant.now()));
            orders.add(new Order(null, user, OrderStatus.CANCELED, Instant.now()));

            orderRepository.saveAll(orders);
            /**
                Seeding Order Items of the Orders
            */
            ArrayList<OrderItem> orderItems = new ArrayList<>();

            orderItems.add(new OrderItem(orders.get(0), products.get(0), 2, products.get(0).getPrice()));
            orderItems.add(new OrderItem(orders.get(0), products.get(1), 2, products.get(1).getPrice()));

            orderItems.add(new OrderItem(orders.get(1), products.get(2), 2, products.get(2).getPrice()));
            orderItems.add(new OrderItem(orders.get(1), products.get(3), 2, products.get(3).getPrice()));

            orderItems.add(new OrderItem(orders.get(2), products.get(4), 2, products.get(4).getPrice()));
            orderItems.add(new OrderItem(orders.get(2), products.get(5), 2, products.get(5).getPrice()));

            orderItems.add(new OrderItem(orders.get(3), products.get(6), 2, products.get(6).getPrice()));
            orderItems.add(new OrderItem(orders.get(3), products.get(7), 2, products.get(7).getPrice()));

            orderItems.add(new OrderItem(orders.get(4), products.get(8), 2, products.get(8).getPrice()));
            orderItems.add(new OrderItem(orders.get(4), products.get(9), 2, products.get(9).getPrice()));

            orderItemRepository.saveAll(orderItems);
            /**
                Setting up the payment of the 3rd Order
            */
            orders.get(3).setPayment(new Payment(null, orders.get(3), Instant.now()));
            orderRepository.save(orders.get(3));
        } catch (Exception e) {
            throw new Exception("something went wrong");
        }
    }
}