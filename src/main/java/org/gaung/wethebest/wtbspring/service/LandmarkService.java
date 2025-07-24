package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.LandmarkResponse;
import org.springframework.data.domain.Page;

public interface LandmarkService {

    Page<LandmarkResponse> getAllLandmark(int page, int size);
}
