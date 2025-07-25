package org.gaung.wethebest.wtbspring.service;

import lombok.RequiredArgsConstructor;
import org.gaung.wethebest.wtbspring.dto.ActivityRequest;
import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.model.Activity;
import org.gaung.wethebest.wtbspring.model.Location;
import org.gaung.wethebest.wtbspring.model.Marker;
import org.gaung.wethebest.wtbspring.projection.ActivityDistanceProjection;
import org.gaung.wethebest.wtbspring.repository.ActivityRepository;
import org.gaung.wethebest.wtbspring.repository.LocationRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final PictureService pictureService;

    private final LocationRepository locationRepository;

    @Override
    public Page<ActivityResponse> getNearbyActivities(double latitude, double longitude, int page, int size) {
        Pageable pageable = PageRequest.of(processPage(page), size);
        Page<ActivityDistanceProjection> results = activityRepository.findNearbyActivities(latitude, longitude, pageable);

        return results.map(p -> {
            CoordinatesResponse coordinates = new CoordinatesResponse();
            coordinates.setLatitude(p.getLatitude());
            coordinates.setLongitude(p.getLongitude());

            return ActivityResponse.builder()
                    .id(p.getActivityId())
                    .name(p.getName())
                    .description(p.getDescription())
                    .contact(p.getContact())
                    .url(p.getUrl())
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
    public void createActivity(ActivityRequest request) {
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

        Activity activity = new Activity();
        activity.setMarker(marker);
        activityRepository.save(activity);
    }

    @Override
    public void deleteActivity(UUID activityId) {
        activityRepository.findById(activityId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        activityRepository.deleteById(activityId);
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(longitude, latitude));
    }
}
