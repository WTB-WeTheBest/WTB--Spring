package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.LandmarkRequest;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LandmarkService {

    Page<LandmarkResponse> getNearbyLandmarks(double latitude, double longitude, int page, int size);

    void createLandmark(LandmarkRequest request);

    void deleteLandmark(UUID landmarkId);
}
