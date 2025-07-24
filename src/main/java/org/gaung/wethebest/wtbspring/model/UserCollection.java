package org.gaung.wethebest.wtbspring.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_have_collection")
public class UserCollection {

    @EmbeddedId
    private UserCollectionId id;

    @ManyToOne
    @MapsId("idMarker")
    @JoinColumn(name = "id_marker")
    private Marker marker;
}
