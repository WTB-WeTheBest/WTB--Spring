package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.dto.ActivityResponse;
import org.springframework.data.domain.Page;

public interface ActivityService {

    Page<ActivityResponse> getAllActivity(int page, int size);
}
