package com.example.bop.tenant.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String state;
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    private String phone;
    private String email;

    @Builder.Default
    private String timezone = "Asia/Kolkata";

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}
