package com.prodig.micro.stock.service;


import com.prodig.micro.basedomain.Order;
import com.prodig.micro.stock.domain.Product;
import com.prodig.micro.stock.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OrderManageService {

    private static final String SOURCE = "stock";
    private static final Logger LOG = LoggerFactory.getLogger(OrderManageService.class);
   @Autowired

    private ProductRepository repository;
    @Autowired
    private KafkaTemplate<Long, Order> template;



    public void reserve(Order order) {
        Product product = repository.findById(order.getProductId()).orElseThrow();
        LOG.info("Found: {}");
        if (order.getStatus().equals("NEW")) {
            if (order.getProductCount() < product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.getProductCount());
                product.setAvailableItems(product.getAvailableItems() - order.getProductCount());
                order.setStatus("ACCEPT");
                repository.save(product);
            } else {
                order.setStatus("REJECT");
            }
            template.send("stock-orders", order.getId(), order);
            LOG.info("Sent: {}", order);
        }
    }
    public void saveProduct()
    {
        Product product = new Product();
        product.setName("Car");
        product.setAvailableItems(5);
        product.setReservedItems(5);
        Product productMnaged = repository.save(product);

        System.out.println("     " + productMnaged.getId());


    }
    public Product findProduct(Long id )
    {

        Product productMnaged = repository.findById(id).get();

       return productMnaged;
    }
    public void confirm(Order order) {
        Product product = repository.findById(order.getProductId()).orElseThrow();
        LOG.info("Found: {}");
        if (order.getStatus().equals("CONFIRMED")) {
            product.setReservedItems(product.getReservedItems() - order.getProductCount());
            repository.save(product);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.getProductCount());
            product.setAvailableItems(product.getAvailableItems() + order.getProductCount());
            repository.save(product);
        }
    }

}
