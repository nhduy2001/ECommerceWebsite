package com.duy.assignment.service;

import com.duy.assignment.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO, String username);
    List<OrderDTO> getOder(String username);
    List<OrderDTO> getAllOrders();
    void deleteOrder(int orderId);
    OrderDTO updateStatus(int orderId);
}
