package com.prodig.micro.stock.web;

//import lombok.extern.slf4j.Slf4j;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.stock.domain.Product;
import com.prodig.micro.stock.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

///mvn archetype:generate -DgroupId=com.microservices -DartifactId=microservices -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
//@Slf4j
@RestController
@RequestMapping("api/v1/stock")
public class StockerServiceController
{
    @Autowired
    OrderManageService orderManageService;

    @GetMapping(value = "/save")
    public String checkingServer()

    {
        //log.info("  this is calling frausde ");
        orderManageService.saveProduct();
        return  " Order saved ";
    }
    @GetMapping(value = "/getSingleProduct/{productId}")
    public Product getSingleProduct(@PathVariable("productId") Long productId)
    {

        System.out.println("  this is just PAYMENT customerId through client  " + productId);
        return  orderManageService.findProduct(productId);
    }


}
