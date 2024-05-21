package com.duy.assignment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    int productId;

    @Column(length = 100)
    String name;

    String description;

    @Column(length = 100)
    String image;

    @Column(name = "average_rating")
    double averageRating;

    int price;

    @Column(length = 30)
    String color;

    @Column(name = "screen_size")
    double screenSize;

    @Column(name = "ram")
    int ram;

    @Column(name = "internal_storage")
    int internalStorage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    Set<Category> categories = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    Brand brand;
}
