package org.gaung.wethebest.wtbspring.controller;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.PageInfo;
import org.gaung.wethebest.wtbspring.dto.WebResponse;
import org.gaung.wethebest.wtbspring.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<ActivityResponse> activities = activityService.getAllActivity(page, size);

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
}
