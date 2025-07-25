package org.gaung.wethebest.wtbspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LandmarkRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String contact;

    @NotBlank
    private String url;

    private int minPrice;

    private int maxPrice;

    @NotBlank
    private String story;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotBlank
    private String city;

    @NotBlank
    private String province;
}
