package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.model.Activity;
import org.gaung.wethebest.wtbspring.model.Landmark;
import org.gaung.wethebest.wtbspring.model.Location;
import org.gaung.wethebest.wtbspring.model.Marker;
import org.gaung.wethebest.wtbspring.model.Picture;
import org.gaung.wethebest.wtbspring.repository.LandmarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public LandmarkServiceImpl(LandmarkRepository landmarkRepository) {
        this.landmarkRepository = landmarkRepository;
    }

    @Override
    public Page<LandmarkResponse> getAllLandmark(int page, int size) {
        Pageable pageable = PageRequest.of(processPage(page), size);
        Page<Landmark> landmarks = landmarkRepository.findAll(pageable);

        List<LandmarkResponse> responseList = landmarks.stream()
                .map(this::mapToLandmarkResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, landmarks.getTotalElements());
    }

    private LandmarkResponse mapToLandmarkResponse(Landmark landmark) {
        Marker marker = landmark.getMarker();
        Location location = marker.getLocation();

        double latitude = location.getCoordinates().getY();
        double longitude = location.getCoordinates().getX();

        CoordinatesResponse coordinatesResponse = new CoordinatesResponse();
        coordinatesResponse.setLatitude(latitude);
        coordinatesResponse.setLongitude(longitude);

        List<Picture> picture = marker.getPictures();

        List<String> pictureResponse = picture.stream()
                .map(this::mapToPictureResponse)
                .collect(Collectors.toList());

        return LandmarkResponse.builder()
                .id(marker.getId())
                .name(marker.getName())
                .description(marker.getDescription())
                .contact(marker.getContact())
                .url(marker.getUrl())
                .minPrice(marker.getMinPrice())
                .maxPrice(marker.getMaxPrice())
                .story(landmark.getStory())
                .coordinates(coordinatesResponse)
                .pictures(pictureResponse)
                .build();
    }

    private String mapToPictureResponse(Picture picture) {
        return picture.getUrl();
    }

    private int processPage(int page) {
        return page - 1;
    }
}
