package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.OrderDTO;
import com.duy.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/orders")
public class CustomerOrderController {
    private final OrderService orderService;

    @Autowired
    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDTO addOrder(@RequestBody OrderDTO orderDTO, Principal principal) {
        return orderService.addOrder(orderDTO, principal.getName());
    }

    @GetMapping
    public List<OrderDTO> getOrders(Principal principal) {
        return orderService.getOder(principal.getName());
    }
}
