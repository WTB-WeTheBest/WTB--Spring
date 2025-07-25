package org.gaung.wethebest.wtbspring.repository;

import org.gaung.wethebest.wtbspring.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
}
