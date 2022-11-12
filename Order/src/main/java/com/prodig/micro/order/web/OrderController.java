package com.prodig.micro.order.web;

//import lombok.extern.slf4j.Slf4j;
import com.prodig.micro.basedomain.Order;
import com.prodig.micro.order.service.OrderGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
///mvn archetype:generate -DgroupId=com.microservices -DartifactId=microservices -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
/// mvn clean install
//@Slf4j
@RestController
@RequestMapping("api/v1/order")
public class OrderController
{
//    @Autowired
//    CustomerService customerService;

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
    private AtomicLong id = new AtomicLong();
    @Autowired
    private KafkaTemplate<Long, Order> template;
    @Autowired
    private StreamsBuilderFactoryBean kafkaStreamsFactory;
    @Autowired
    private OrderGeneratorService orderGeneratorService;

    @PostMapping("/generatebypost")
    public Order create(@RequestBody Order order) {
        order.setId(id.incrementAndGet());
        template.send("orders", order.getId(), order);
        LOG.info("Sent: {}", order);
        return order;
    }

    @GetMapping("/generate")
    public boolean create() {
        orderGeneratorService.generate();
        return true;
    }

    @GetMapping(value = "/getAll")
    public List<Order> allOrders() {
        List<Order> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<Long, Order> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(
                        "orders",
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, Order> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));
        return orders;
    }
  @GetMapping(value = "/testing")
    public Order checkingServer()
  {
    System.out.println("  this is just checking  ");
         Order  order =   new Order();
        order.setCustomerId(45L);
        order.setPrice(45);
        return  order;
    }

}
