package com.duy.assignment.service.impl;

import com.duy.assignment.dto.OrderDTO;
import com.duy.assignment.dto.OrderDetailsDTO;
import com.duy.assignment.entity.CartDetails;
import com.duy.assignment.entity.Order;
import com.duy.assignment.entity.OrderDetails;
import com.duy.assignment.entity.enumType.OrderPayType;
import com.duy.assignment.entity.enumType.OrderStatus;
import com.duy.assignment.mapper.OrderDetailsMapper;
import com.duy.assignment.mapper.OrderMapper;
import com.duy.assignment.repository.*;
import com.duy.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplement implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailsMapper orderDetailsMapper;

    @Autowired
    public OrderServiceImplement(OrderRepository orderRepository,
                                 OrderDetailsRepository orderDetailsRepository,
                                 CartDetailsRepository cartDetailsRepository,
                                 CartRepository cartRepository,
                                 UserRepository userRepository,
                                 OrderMapper orderMapper,
                                 OrderDetailsMapper orderDetailsMapper) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderMapper = orderMapper;
        this.orderDetailsMapper = orderDetailsMapper;
    }

    @Override
    @Transactional
    public OrderDTO addOrder(OrderDTO orderDTO, String username) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setPay(false);
        order.setPayType(OrderPayType.CASH);
        order.setStatus(OrderStatus.ORDERED);
        order.setUser(userRepository.findUserByUsername(username).get());
        orderRepository.save(order);

        int existCartId = cartRepository.findCartByUser_Username(username).get().getCartId();

        List<CartDetails> cartDetails = cartDetailsRepository.findAllByCart_CartId(existCartId);

        for (CartDetails cartDetail : cartDetails) {
            cartDetail.setComplete(true);
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setProduct(cartDetail.getProduct());
            orderDetails.setColorId(cartDetail.getColorId());
            orderDetails.setQuantity(cartDetail.getQuantity());
            orderDetailsRepository.save(orderDetails);
        }

        cartDetailsRepository.deleteByIsCompleteTrue();
        cartRepository.deleteById(existCartId);

        return orderMapper.ToDTO(order);
    }

    @Override
    public List<OrderDTO> getOder(String username) {
        List<Order> orders = orderRepository.findAllByUser_Username(username);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderMapper.ToDTO(order);
            List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();

            for (OrderDetails orderDetails : order.getOrderDetails()) {
                OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDTO(orderDetails);
                orderDetailsDTO.setProductId(orderDetails.getProduct().getProductId());
                orderDetailsDTO.setQuantity(orderDetails.getQuantity());
                orderDetailsDTOs.add(orderDetailsDTO);
            }
            orderDTO.setOrderDetailsDTOs(orderDetailsDTOs);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderMapper.toDTOs(orderRepository.findAll());
    }

    @Override
    public void deleteOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO updateStatus(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        switch (order.getStatus()) {
            case OrderStatus.ORDERED -> order.setStatus(OrderStatus.DELIVERING);
            case OrderStatus.DELIVERING -> {
                order.setStatus(OrderStatus.COMPLETED);
                order.setPay(true);
            }
        }
        orderRepository.save(order);
        return orderMapper.ToDTO(order);
    }
}
