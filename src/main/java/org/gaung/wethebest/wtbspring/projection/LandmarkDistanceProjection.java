package org.gaung.wethebest.wtbspring.projection;

import java.util.UUID;

public interface LandmarkDistanceProjection {

    UUID getLandmarkId();

    UUID getMarkerId();

    String getName();

    String getStory();

    String getDescription();

    String getContact();

    String getUrl();

    int getMinPrice();

    int getMaxPrice();

    Double getLatitude();

    Double getLongitude();

    Double getDistance();

    String getCity();

    String getProvince();
}
