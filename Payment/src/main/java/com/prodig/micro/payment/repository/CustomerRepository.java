package com.prodig.micro.payment.repository;

import com.prodig.micro.payment.domain.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long> {
}