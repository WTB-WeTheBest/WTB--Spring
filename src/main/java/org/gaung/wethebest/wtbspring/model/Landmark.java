package org.gaung.wethebest.wtbspring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "landmark")
public class Landmark {

    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String story;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Marker marker;
}

