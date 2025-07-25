package org.gaung.wethebest.wtbspring.repository;

import org.gaung.wethebest.wtbspring.model.Landmark;
import org.gaung.wethebest.wtbspring.projection.LandmarkDistanceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, UUID> {

    @Query(value = """
    SELECT 
        m.id AS markerId,
        a.id AS landmarkId,
        a.story,
        m.name,
        m.description,
        m.contact,
        m.url,
        m.min_price,
        m.max_price,
        ST_Y(l.coordinates::geometry) AS latitude,
        ST_X(l.coordinates::geometry) AS longitude,
        ST_Distance(
            l.coordinates,
            ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography
        ) AS distance
    FROM landmark a
    JOIN marker m ON a.id = m.id
    JOIN location l ON m.id_location = l.id
    ORDER BY distance
    """,
            countQuery = """
        SELECT COUNT(*) 
        FROM landmark a
        JOIN marker m ON a.id = m.id
        JOIN location l ON m.id_location = l.id
    """,
            nativeQuery = true)
    Page<LandmarkDistanceProjection> findNearbyLandmarks(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            Pageable pageable);
}
