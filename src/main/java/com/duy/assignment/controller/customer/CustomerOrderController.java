package com.duy.assignment.controller.customer;

import com.duy.assignment.dto.OrderDTO;
import com.duy.assignment.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customer/orders")
public class CustomerOrderController {
    private final OrderService orderService;

    @Autowired
    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderDTO orderDTO, Principal principal) {
        return ResponseEntity.ok(orderService.addOrder(orderDTO, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<?> getOrders(Principal principal) {
        return ResponseEntity.ok(orderService.getOder(principal.getName()));
    }
}
