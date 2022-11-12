package com.prodig.micro.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
@EnableKafkaStreams
public class OrderMain
{
   public static void main(String[] args)
   {

      SpringApplication.run(OrderMain.class , args);
   }

}
