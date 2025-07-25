package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.ActivityRequest;
import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ActivityService {

    Page<ActivityResponse> getNearbyActivities(double latitude, double longitude, int page, int size);

    void createActivity(ActivityRequest request);

    void deleteActivity(UUID activityId);

    ActivityResponse getActivityById(UUID activityId);
}
