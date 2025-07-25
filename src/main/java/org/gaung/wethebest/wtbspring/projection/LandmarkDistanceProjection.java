package org.gaung.wethebest.wtbspring.projection;

import java.util.UUID;

public interface LandmarkDistanceProjection {

    UUID getActivityId();

    UUID getMarkerId();

    String getName();

    String getStory();

    String getDescription();

    String getContact();

    String getUrl();

    String getMinPrice();

    String getMaxPrice();

    Double getLatitude();

    Double getLongitude();

    Double getDistance();
}
