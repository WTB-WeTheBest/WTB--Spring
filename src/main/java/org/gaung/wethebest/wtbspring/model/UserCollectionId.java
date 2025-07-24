package org.gaung.wethebest.wtbspring.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollectionId implements Serializable {

    private UUID idUser;

    private UUID idMarker;
}
