package org.gaung.wethebest.wtbspring.service;

import java.util.List;
import java.util.UUID;

public interface PictureService {

    List<String> getPicturesByMarkerId(UUID markerId);
}
