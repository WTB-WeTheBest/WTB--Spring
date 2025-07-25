package org.gaung.wethebest.wtbspring.projection;

import java.util.UUID;

public interface ActivityDistanceProjection {

    UUID getActivityId();

    UUID getMarkerId();

    String getName();

    String getDescription();

    String getContact();

    String getUrl();

    String getMinPrice();

    String getMaxPrice();

    Double getLatitude();

    Double getLongitude();

    Double getDistance();
}
