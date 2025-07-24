package org.gaung.wethebest.wtbspring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class LandmarkResponse {

    private UUID id;

    private String name;

    private String description;

    private String contact;

    private String url;

    private String minPrice;

    private String maxPrice;

    private String story;

    private CoordinatesResponse coordinates;

    private List<String> pictures;
}
