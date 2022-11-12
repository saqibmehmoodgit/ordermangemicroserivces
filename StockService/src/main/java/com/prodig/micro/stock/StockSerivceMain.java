package com.prodig.micro.stock;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.stock.service.OrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class StockSerivceMain
{
   @Autowired
   OrderManageService orderManageService;
   private static final Logger LOG = LoggerFactory.getLogger(StockSerivceMain.class);
   public static void main(String[] args)
   {

      SpringApplication.run(StockSerivceMain.class , args);
   }

   @KafkaListener(id = "orders", topics = "orders", groupId = "stock")
   public void onEvent(Order o) {

      System.out.println("  this is onEvent  of  sorck service ");
      LOG.info("Received: {}" , o);
      if (o.getStatus().equals("NEW"))
         orderManageService.reserve(o);
      else
         orderManageService.confirm(o);
   }

}
