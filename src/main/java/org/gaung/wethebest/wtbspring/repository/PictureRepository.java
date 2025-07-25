package org.gaung.wethebest.wtbspring.repository;

import org.gaung.wethebest.wtbspring.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, UUID> {

    @Query("SELECT p.url FROM Picture p WHERE p.marker.id = :markerId")
    List<String> findAllUrlsByMarkerId(@Param("markerId") UUID markerId);
}
