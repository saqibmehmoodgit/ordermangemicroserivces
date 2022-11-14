package com.prodig.micro.clients.stock;

import com.prodig.micro.payment.domain.Customer;
import com.prodig.micro.stock.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stockservice")
public interface StockCLient
{

    @GetMapping(value = "api/v1/stock/getSingleProduct/{productId}")
    Product getSingleProduct(@PathVariable("productId") Long productId);

}


