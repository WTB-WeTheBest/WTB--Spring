package org.gaung.wethebest.wtbspring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marker")
public class Marker {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private String contact;

    private String url;

    @Column(name = "min_price")
    private int minPrice;

    @Column(name = "max_price")
    private int maxPrice;

    @OneToOne
    @JoinColumn(name = "id_location")
    private Location location;

    @OneToMany(mappedBy = "marker")
    private List<Picture> pictures;
}
