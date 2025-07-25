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

    private int minPrice;

    private int maxPrice;

    private String story;

    private LocationResponse location;

    private List<String> pictures;

    private double distance;
}
