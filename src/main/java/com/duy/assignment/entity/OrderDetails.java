package com.duy.assignment.entity;

import com.duy.assignment.entity.enumType.OrderPayType;
import com.duy.assignment.entity.enumType.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    int orderDetailId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type")
    private OrderPayType payType;

    @Column(name = "total_price")
    int totalPrice;

    @Column(name = "is_pay")
    boolean isPay;

    String detail;

    @Column(name = "total_quantity")
    int totalQuantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    Order order;
}
