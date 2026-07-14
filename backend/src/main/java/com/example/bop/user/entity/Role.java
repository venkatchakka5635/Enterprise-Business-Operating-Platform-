package com.example.bop.user.entity;

import com.example.bop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(columnDefinition = "jsonb")
    private String permissions;

    @Column(name = "is_system")
    @Builder.Default
    private Boolean isSystem = false;
}
