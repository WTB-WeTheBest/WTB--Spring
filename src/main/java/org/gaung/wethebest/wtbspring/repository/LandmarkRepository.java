package org.gaung.wethebest.wtbspring.repository;

import org.gaung.wethebest.wtbspring.model.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, UUID> {
}
