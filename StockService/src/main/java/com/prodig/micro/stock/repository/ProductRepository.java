package com.prodig.micro.stock.repository;


import com.prodig.micro.stock.domain.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long> {
}
