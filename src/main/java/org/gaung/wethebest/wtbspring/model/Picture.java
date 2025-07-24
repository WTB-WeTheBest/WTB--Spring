package org.gaung.wethebest.wtbspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "picture")
public class Picture {

    @Id
    private UUID id;

    private String url;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "id_marker")
    private Marker marker;
}
