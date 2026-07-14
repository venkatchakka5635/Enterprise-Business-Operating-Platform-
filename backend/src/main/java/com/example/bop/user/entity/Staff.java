package com.example.bop.user.entity;

import com.example.bop.common.entity.BaseEntity;
import com.example.bop.tenant.entity.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "staff")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Staff extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    private String specialization;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(name = "working_hours", columnDefinition = "jsonb")
    private String workingHours;

    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;
}
