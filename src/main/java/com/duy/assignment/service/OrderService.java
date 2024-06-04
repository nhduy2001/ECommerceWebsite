package com.duy.assignment.service;

import com.duy.assignment.dto.OrderDTO;

public interface OrderService {
    OrderDTO addOrder(OrderDTO orderDTO, String username);
}
