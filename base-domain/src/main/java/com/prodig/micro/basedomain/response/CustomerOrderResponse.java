package com.prodig.micro.basedomain.response;

import lombok.Data;

@Data
public class CustomerOrderResponse
{

    private String nameProduct;
    private String nameCustomer;
    private int productCount;
    private int price;
    private String status;


}
