package com.prodig.micro.clients.customer;

import com.prodig.micro.payment.domain.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment")
public interface CustomerCLient
{
//         @GetMapping(path = "api/v1/payment/getSingleCustomer/{customerId}")
//         Customer getSingleCustomer(@PathVariable("customerId") Long customerId );

    @GetMapping(value = "api/v1/payment/getSingleCustomer/{customerId}")
    Customer getSingleCustomer(@PathVariable("customerId") Long customerId);

}


