package com.duy.assignment.entity;

import com.duy.assignment.entity.enumType.OrderPayType;
import com.duy.assignment.entity.enumType.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    String address;

    @Column(name = "total_price")
    int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type")
    private OrderPayType payType;

    @Column(name = "is_pay")
    boolean isPay;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;
}
