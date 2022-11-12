package com.prodig.micro.order.service;

import com.prodig.micro.basedomain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderGeneratorService {

    private static Random RAND = new Random();
    private AtomicLong id = new AtomicLong();
    @Autowired
    private Executor executor;
    @Autowired
    private KafkaTemplate<Long, Order> template;

    @Async
    public void generate() {
        for (int i = 0; i < 3; i++) {
            int x = RAND.nextInt(5) + 1;
            Order o = new Order( (long) i, (long) i, (long) i, "NEW"); //id.incrementAndGet()
            o.setPrice(100 * i);
            o.setProductCount(i);
            template.send("orders", o.getId(), o);
        }
    }
}
