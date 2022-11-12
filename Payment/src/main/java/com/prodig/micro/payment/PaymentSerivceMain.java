package com.prodig.micro.payment;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.payment.domain.Customer;
import com.prodig.micro.payment.repository.CustomerRepository;
import com.prodig.micro.payment.service.OrderManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class PaymentSerivceMain
{

   private static final Logger LOG = LoggerFactory.getLogger(PaymentSerivceMain.class);
   public static void main(String[] args)
   {

      SpringApplication.run(PaymentSerivceMain.class , args);
   }


   @Autowired
   OrderManageService orderManageService;

   @KafkaListener(id = "orders", topics = "orders", groupId = "payment")
   public void onEvent(Order o) {
      System.out.println("  this is onEvent  of  payment service ");
      LOG.info("Received: {}" , o);
      if (o.getStatus().equals("NEW"))
         orderManageService.reserve(o);
      else
         orderManageService.confirm(o);
   }

   @Autowired
   private CustomerRepository repository;

   @PostConstruct
   public void generateData() {
      Random r = new Random();

      for (int i = 0; i < 100; i++) {
         int count = r.nextInt(1000);
         Customer c = new Customer(null, "saqib"+count, count, 0);
         repository.save(c);
      }
   }

/// ++++Fatima1
}
