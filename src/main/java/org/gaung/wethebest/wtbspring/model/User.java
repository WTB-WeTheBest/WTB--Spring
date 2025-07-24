package org.gaung.wethebest.wtbspring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    private String email;

    private String username;

    private String password;

    @Column(name = "is_admin")
    private String isAdmin;

    @Column(name = "joined_at")
    private OffsetDateTime joinedAt;
}
