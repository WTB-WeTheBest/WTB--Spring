package org.gaung.wethebest.wtbspring.controller;

import jakarta.validation.Valid;
import org.gaung.wethebest.wtbspring.dto.LandmarkRequest;
import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.gaung.wethebest.wtbspring.dto.PageInfo;
import org.gaung.wethebest.wtbspring.dto.WebResponse;
import org.gaung.wethebest.wtbspring.security.annotation.AllowedRoles;
import org.gaung.wethebest.wtbspring.service.LandmarkService;
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
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<LandmarkResponse> landmarks = landmarkService.getNearbyLandmarks(lat, lng, page, size);

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

    @GetMapping(
            path = "/landmarks/{landmarkId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<LandmarkResponse>> getLandmarkById(@PathVariable UUID landmarkId) {

        LandmarkResponse landmark = landmarkService.getLandmarkById(landmarkId);

        WebResponse<LandmarkResponse> response = WebResponse.<LandmarkResponse>builder()
                .data(landmark)
                .build();

        return ResponseEntity.ok(response);
    }

    @AllowedRoles({"ADMIN"})
    @PostMapping(
            path = "/landmarks",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> createLandmark(
            @Valid @RequestBody LandmarkRequest landmarkRequest) {

        landmarkService.createLandmark(landmarkRequest);

        WebResponse<String> response = WebResponse.<String>builder()
                .data("Created")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @AllowedRoles({"ADMIN"})
    @DeleteMapping(
            path = "/landmarks/{landmarkId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deleteLandmark(@PathVariable UUID landmarkId) {

        landmarkService.deleteLandmark(landmarkId);

        WebResponse<String> response = WebResponse.<String>builder()
                .data("OK")
                .build();

        return ResponseEntity.ok(response);
    }
}
