package com.duy.assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id;

    private String username;

    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String role;

    private boolean enabled;

}
