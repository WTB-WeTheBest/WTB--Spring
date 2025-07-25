package org.gaung.wethebest.wtbspring.service;

import lombok.RequiredArgsConstructor;
import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.LocationResponse;
import org.gaung.wethebest.wtbspring.dto.LandmarkRequest;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.model.Activity;
import org.gaung.wethebest.wtbspring.model.Landmark;
import org.gaung.wethebest.wtbspring.model.Location;
import org.gaung.wethebest.wtbspring.model.Marker;
import org.gaung.wethebest.wtbspring.model.Picture;
import org.gaung.wethebest.wtbspring.projection.LandmarkDistanceProjection;
import org.gaung.wethebest.wtbspring.repository.LandmarkRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
            LocationResponse location = new LocationResponse();
            location.setLatitude(p.getLatitude());
            location.setLongitude(p.getLongitude());
            location.setCity(p.getCity());
            location.setProvince(p.getProvince());

            return LandmarkResponse.builder()
                    .id(p.getLandmarkId())
                    .name(p.getName())
                    .description(p.getDescription())
                    .contact(p.getContact())
                    .url(p.getUrl())
                    .story(p.getStory())
                    .minPrice(p.getMinPrice())
                    .maxPrice(p.getMaxPrice())
                    .location(location)
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

    @Override
    public void deleteLandmark(UUID landmarkId) {
        landmarkRepository.findById(landmarkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        landmarkRepository.deleteById(landmarkId);
    }

    private Point createPoint(double latitude, double longitude) {
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(longitude, latitude));
    }

    @Override
    public LandmarkResponse getLandmarkById(UUID landmarkId) {
        Landmark landmark = landmarkRepository.findById(landmarkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        Marker marker = landmark.getMarker();

        Location p = marker.getLocation();
        LocationResponse location = new LocationResponse();
        location.setLatitude(p.getCoordinates().getY());
        location.setLongitude(p.getCoordinates().getX());
        location.setCity(p.getCity());
        location.setProvince(p.getProvince());

        List<String> pictureUrls = marker.getPictures()
                .stream()
                .map(Picture::getUrl)
                .collect(Collectors.toList());

        return LandmarkResponse.builder()
                .id(landmark.getId())
                .name(marker.getName())
                .description(marker.getDescription())
                .contact(marker.getContact())
                .url(marker.getUrl())
                .minPrice(marker.getMinPrice())
                .maxPrice(marker.getMaxPrice())
                .location(location)
                .pictures(pictureUrls)
                .story(landmark.getStory())
                .build();
    }
}
