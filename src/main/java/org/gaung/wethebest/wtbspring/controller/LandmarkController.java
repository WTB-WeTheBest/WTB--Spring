package org.gaung.wethebest.wtbspring.controller;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.dto.PageInfo;
import org.gaung.wethebest.wtbspring.dto.WebResponse;
import org.gaung.wethebest.wtbspring.service.LandmarkService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LandmarkController {

    private final LandmarkService landmarkService;

    public LandmarkController(LandmarkService landmarkService) {
        this.landmarkService = landmarkService;
    }

    @GetMapping(
            path = "/landmarks",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<LandmarkResponse>>> getAllLandmarks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<LandmarkResponse> landmarks = landmarkService.getAllLandmark(page, size);

        return getPaginationResponse(landmarks);
    }

    private ResponseEntity<WebResponse<List<LandmarkResponse>>> getPaginationResponse(Page<LandmarkResponse> page) {
        PageInfo pageInfo = PageInfo.builder()
                .size(page.getSize())
                .currentPage(processCurrentPage(page.getNumber()))
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();

        WebResponse<List<LandmarkResponse>> response = WebResponse.<List<LandmarkResponse>>builder()
                .data(page.getContent())
                .pageInfo(pageInfo)
                .build();

        return ResponseEntity.ok(response);
    }

    private int processCurrentPage(int currentPage) {
        return currentPage + 1;
    }
}
