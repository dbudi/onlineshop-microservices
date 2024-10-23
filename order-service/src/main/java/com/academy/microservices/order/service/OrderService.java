package com.academy.microservices.order.service;

import com.academy.microservices.order.client.InventoryClient;
import com.academy.microservices.order.dto.OrderRequest;
import com.academy.microservices.order.model.Order;
import com.academy.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        var order = mapToOrder(orderRequest);

        if(isProductInStock) {
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Product with skuCode="+orderRequest.skuCode()+" is Out of Stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}
