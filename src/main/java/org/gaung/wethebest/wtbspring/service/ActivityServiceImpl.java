package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.model.Activity;
import org.gaung.wethebest.wtbspring.model.Location;
import org.gaung.wethebest.wtbspring.model.Marker;
import org.gaung.wethebest.wtbspring.model.Picture;
import org.gaung.wethebest.wtbspring.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Page<ActivityResponse> getAllActivity(int page, int size) {
        Pageable pageable = PageRequest.of(processPage(page), size);
        Page<Activity> activities = activityRepository.findAll(pageable);

        List<ActivityResponse> responseList = activities.stream()
                .map(this::mapToActivityResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, activities.getTotalElements());
    }

    private ActivityResponse mapToActivityResponse(Activity activity) {
        Marker marker = activity.getMarker();
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

        return ActivityResponse.builder()
                .id(marker.getId())
                .name(marker.getName())
                .description(marker.getDescription())
                .contact(marker.getContact())
                .url(marker.getUrl())
                .minPrice(marker.getMinPrice())
                .maxPrice(marker.getMaxPrice())
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
