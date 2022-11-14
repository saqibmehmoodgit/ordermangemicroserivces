package com.prodig.micro.order.service;


import com.prodig.micro.basedomain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class OrderManageService {

    public Order confirm(Order orderPayment, Order orderStock) {
        System.out.println("   --------  ------------------------------------ ");
        System.out.println("   --------  this is confrim order ordermanege method in Order service ");
        System.out.println("   --------  ------------------------------------ ");

        Order o = new Order(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getProductId(),
                orderPayment.getProductCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals("ACCEPT") &&
                orderStock.getStatus().equals("ACCEPT")) {
            o.setStatus("CONFIRMED");
        } else if (orderPayment.getStatus().equals("REJECT") &&
                orderStock.getStatus().equals("REJECT")) {
            o.setStatus("REJECTED");
        } else if (orderPayment.getStatus().equals("REJECT") ||
                orderStock.getStatus().equals("REJECT")) {
            String source = orderPayment.getStatus().equals("REJECT")
                    ? "PAYMENT" : "STOCK";
            o.setStatus("ROLLBACK");
            o.setSource(source);
        }
        return o;
    }

}