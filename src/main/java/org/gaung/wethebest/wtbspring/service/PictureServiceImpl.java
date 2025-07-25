package org.gaung.wethebest.wtbspring.service;

import org.gaung.wethebest.wtbspring.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<String> getPicturesByMarkerId(UUID markerId) {
        return pictureRepository.findAllUrlsByMarkerId(markerId);
    }
}
