package org.gaung.wethebest.wtbspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.gaung.wethebest.wtbspring.model.Location;

@Data
@AllArgsConstructor
public class ActivityRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String contact;

    @NotBlank
    private String url;

    @NotBlank
    private String minPrice;

    @NotBlank
    private String maxPrice;

    @NotNull
    private Location location;
}
