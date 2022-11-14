package com.prodig.micro.payment.service;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.payment.domain.Customer;
import com.prodig.micro.payment.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OrderManageService {

    private static final String SOURCE = "payment";
    private static final Logger LOG = LoggerFactory.getLogger(OrderManageService.class);
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private KafkaTemplate<Long, Order> template;

//    public OrderManageService(CustomerRepository repository, KafkaTemplate<Long, Order> template) {
//        this.repository = repository;
//        this.template = template;
//    }

    public void reserve(Order order) {
        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
        LOG.info("Found: {}", customer);
        if (order.getPrice() < customer.getAmountAvailable()) {
            order.setStatus("ACCEPT");
            customer.setAmountReserved(customer.getAmountReserved() + order.getPrice());
            customer.setAmountAvailable(customer.getAmountAvailable() - order.getPrice());
        } else {
            order.setStatus("REJECT");
        }
        order.setSource(SOURCE);
        repository.save(customer);
        template.send("payment-orders", order.getId(), order);
        LOG.info("Sent: {}", order);
    }
    public void saveCustomer()
    {
        System.out.println("  this is id   ");
                Customer customer =  new Customer();
        customer.setName("SAQIB");
        customer.setAmountAvailable(101);
        customer.setAmountReserved(201);
        Customer manged =   repository.save(customer);
        System.out.println("  this is id   " +  manged.getId() +"  name "+  manged.getName() );
    }

    public Customer getCustomer(Long customerId)
    {
        System.out.println("  Payment    " +  customerId );
        Customer manged =   repository.findById(customerId).get();
        System.out.println("  this is id   " +  manged.getId() +"  name "+  manged.getName() );
        return  manged;
    }

    public void confirm(Order order) {
        Customer customer = repository.findById(order.getCustomerId()).orElseThrow();
        LOG.info("Found: {}", customer);
        if (order.getStatus().equals("CONFIRMED")) {
            customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
            repository.save(customer);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            customer.setAmountReserved(customer.getAmountReserved() - order.getPrice());
            customer.setAmountAvailable(customer.getAmountAvailable() + order.getPrice());
            repository.save(customer);
        }

    }
}