package com.prodig.micro.payment.web;

//import lombok.extern.slf4j.Slf4j;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.payment.domain.Customer;
import com.prodig.micro.payment.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

///mvn archetype:generate -DgroupId=com.microservices -DartifactId=microservices -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
//@Slf4j
@RestController
@RequestMapping("api/v1/payment")
public class PaymentServiceController
{
    @Autowired
    OrderManageService orderManageService;

    @GetMapping(value = "/call")
    public String checkingServer()

    {
        //log.info("  this is calling frausde ");
        System.out.println("  this is just PAYMENT  ");
        orderManageService.saveCustomer();
        return  "order is saved ";
    }
    @GetMapping(value = "/getSingleCustomer/{customerId}")
    public Customer getSingleCustomer(@PathVariable Long customerId)
    {
        //log.info("  this is calling frausde ");
        System.out.println("  this is just PAYMENT customerId through feign client ----------- " + customerId);
        return  orderManageService.getCustomer(customerId);
    }

}
