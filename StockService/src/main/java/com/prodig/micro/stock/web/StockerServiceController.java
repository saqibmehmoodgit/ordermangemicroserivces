package com.prodig.micro.stock.web;

//import lombok.extern.slf4j.Slf4j;

import com.prodig.micro.basedomain.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

///mvn archetype:generate -DgroupId=com.microservices -DartifactId=microservices -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
//@Slf4j
@RestController
@RequestMapping("api/v1/payment")
public class StockerServiceController
{
//    @Autowired
//    CustomerService customerService;

    @GetMapping(value = "/call")
    public Order checkingServer()

    {
        //log.info("  this is calling frausde ");
        System.out.println("  this is just fraude  ");
         Order  order =   new Order();
        order.setCustomerId(45L);
        order.setPrice(45);
        return  order;
    }



}
