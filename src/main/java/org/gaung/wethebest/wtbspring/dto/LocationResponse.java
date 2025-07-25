package org.gaung.wethebest.wtbspring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationResponse {

    private double latitude;

    private double longitude;

    private String city;

    private String province;
}
