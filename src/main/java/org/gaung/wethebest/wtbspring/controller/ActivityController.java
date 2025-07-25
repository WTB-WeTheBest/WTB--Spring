package org.gaung.wethebest.wtbspring.controller;

import jakarta.validation.Valid;
import org.gaung.wethebest.wtbspring.dto.ActivityRequest;
import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.PageInfo;
import org.gaung.wethebest.wtbspring.dto.WebResponse;
import org.gaung.wethebest.wtbspring.security.annotation.AllowedRoles;
import org.gaung.wethebest.wtbspring.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(
            path = "/activities",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<ActivityResponse>>> getAllActivity(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ActivityResponse> activities = activityService.getNearbyActivities(lat, lng, page, size);

        return getPaginationResponse(activities);
    }

    private ResponseEntity<WebResponse<List<ActivityResponse>>> getPaginationResponse(Page<ActivityResponse> page) {
        PageInfo pageInfo = PageInfo.builder()
                .size(page.getSize())
                .currentPage(processCurrentPage(page.getNumber()))
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();

        WebResponse<List<ActivityResponse>> response = WebResponse.<List<ActivityResponse>>builder()
                .data(page.getContent())
                .pageInfo(pageInfo)
                .build();

        return ResponseEntity.ok(response);
    }

    private int processCurrentPage(int currentPage) {
        return currentPage + 1;
    }

    @GetMapping(
            path = "/activities/{activityId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<ActivityResponse>> getActivityById(@PathVariable UUID activityId) {

        ActivityResponse activity = activityService.getActivityById(activityId);

        WebResponse<ActivityResponse> response = WebResponse.<ActivityResponse>builder()
                .data(activity)
                .build();

        return ResponseEntity.ok(response);
    }

    @AllowedRoles({"ADMIN"})
    @PostMapping(
            path = "/activities",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> createActivity(
            @Valid @RequestBody ActivityRequest activityRequest) {

        activityService.createActivity(activityRequest);

        WebResponse<String> response = WebResponse.<String>builder()
                .data("Created")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @AllowedRoles({"ADMIN"})
    @DeleteMapping(
            path = "/activities/{activityId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deleteActivity(@PathVariable UUID activityId) {

        activityService.deleteActivity(activityId);

        WebResponse<String> response = WebResponse.<String>builder()
                .data("OK")
                .build();

        return ResponseEntity.ok(response);
    }
}
