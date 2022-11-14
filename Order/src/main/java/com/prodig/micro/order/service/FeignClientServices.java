package com.prodig.micro.order.service;

import com.prodig.micro.basedomain.Order;
import com.prodig.micro.basedomain.response.CustomerOrderResponse;
import com.prodig.micro.clients.customer.CustomerCLient;
import com.prodig.micro.clients.stock.StockCLient;
import com.prodig.micro.payment.domain.Customer;
import com.prodig.micro.stock.domain.Product;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeignClientServices
{
    @Autowired
    CustomerCLient customerClient;
    @Autowired
    StockCLient stockCLient;
    @Autowired
    private StreamsBuilderFactoryBean kafkaStreamsFactory;
    public CustomerOrderResponse getCustomerProduct( int customerId)
    {
        //  customerClient.getSingleCustomer(customerId);  //customerId
        System.out.println(" ----------- FeignClientServices---------- ");
        Order order = singleOrders(customerId);
        Customer customer = getCustomer(order.getCustomerId());  ///
        Product product =getProduct(order.getProductId());   ////
        return customerOrderResponse( customer ,  order , product);
    }

    private Customer getCustomer(long customerId)
    {
        return  customerClient.getSingleCustomer(customerId);
    }
    private Product getProduct(long productId)
    {
        return  stockCLient.getSingleProduct(productId);
    }

    private CustomerOrderResponse customerOrderResponse(Customer customer , Order order ,Product product)
    {
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        customerOrderResponse.setNameCustomer(customer.getName());
        customerOrderResponse.setProductCount(order.getProductCount());
        customerOrderResponse.setNameProduct(product.getName());
        customerOrderResponse.setStatus(order.getStatus());  ///
        customerOrderResponse.setPrice(order.getPrice());
        return  customerOrderResponse;
    }

    public Order singleOrders(int customerId) {
        List<Order> orders = new ArrayList<>();
        Order order = null;
        ReadOnlyKeyValueStore<Long, Order> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(
                        "orders",
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, Order> it = store.all();
        it.forEachRemaining(
                kv -> orders.add(kv.value)
        );
        for (Order order1 : orders)
        {
          if(  order1.getId()==customerId)
          {
              order =order1;
          }
        }

        return order;
    }
}
