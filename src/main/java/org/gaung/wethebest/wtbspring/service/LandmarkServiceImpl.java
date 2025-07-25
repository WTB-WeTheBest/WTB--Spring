package org.gaung.wethebest.wtbspring.service;

import lombok.RequiredArgsConstructor;
import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.dto.LandmarkRequest;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.model.Landmark;
import org.gaung.wethebest.wtbspring.model.Location;
import org.gaung.wethebest.wtbspring.model.Marker;
import org.gaung.wethebest.wtbspring.projection.LandmarkDistanceProjection;
import org.gaung.wethebest.wtbspring.repository.LandmarkRepository;
import org.gaung.wethebest.wtbspring.repository.LocationRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public final PictureService pictureService;

    public final LocationRepository locationRepository;

    @Override
    public Page<LandmarkResponse> getNearbyLandmarks(double latitude, double longitude, int page, int size) {
        Pageable pageable = PageRequest.of(processPage(page), size);
        Page<LandmarkDistanceProjection> results = landmarkRepository.findNearbyLandmarks(latitude, longitude, pageable);

        return results.map(p -> {
            CoordinatesResponse coordinates = new CoordinatesResponse();
            coordinates.setLatitude(p.getLatitude());
            coordinates.setLongitude(p.getLongitude());

            return LandmarkResponse.builder()
                    .id(p.getActivityId())
                    .name(p.getName())
                    .description(p.getDescription())
                    .contact(p.getContact())
                    .url(p.getUrl())
                    .story(p.getStory())
                    .minPrice(p.getMinPrice())
                    .maxPrice(p.getMaxPrice())
                    .coordinates(coordinates)
                    .distance(p.getDistance())
                    .pictures(pictureService.getPicturesByMarkerId(p.getMarkerId()))
                    .build();
        });
    }

    private int processPage(int page) {
        return page - 1;
    }

    @Override
    public void createLandmark(LandmarkRequest request) {
        Location location = new Location();
        location.setCoordinates(createPoint(request.getLatitude(), request.getLongitude()));
        location.setCity(request.getCity());
        location.setProvince(request.getProvince());
        locationRepository.save(location);

        Marker marker = new Marker();
        marker.setName(request.getName());
        marker.setDescription(request.getDescription());
        marker.setContact(request.getContact());
        marker.setUrl(request.getUrl());
        Optional.of(request.getMinPrice())
                .ifPresent(marker::setMinPrice);
        Optional.of(request.getMaxPrice())
                .ifPresent(marker::setMaxPrice);
        marker.setLocation(location);

        Landmark landmark = new Landmark();
        landmark.setMarker(marker);
        landmark.setStory(request.getStory());
        landmarkRepository.save(landmark);
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(longitude, latitude));
    }
}
