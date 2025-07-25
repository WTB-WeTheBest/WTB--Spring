package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.CoordinatesResponse;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.projection.LandmarkDistanceProjection;
import org.gaung.wethebest.wtbspring.repository.LandmarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LandmarkServiceImpl implements LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public final PictureService pictureService;

    public LandmarkServiceImpl(LandmarkRepository landmarkRepository, PictureService pictureService) {
        this.landmarkRepository = landmarkRepository;
        this.pictureService = pictureService;
    }

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
}
