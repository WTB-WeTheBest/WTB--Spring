package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.model.Picture;
import org.gaung.wethebest.wtbspring.projection.ActivityDistanceProjection;
import org.gaung.wethebest.wtbspring.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    private final PictureService pictureService;

    public ActivityServiceImpl(ActivityRepository activityRepository, PictureService pictureService) {
        this.activityRepository = activityRepository;
        this.pictureService = pictureService;
    }


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
}
