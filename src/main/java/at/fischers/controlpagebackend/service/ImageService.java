package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.model.domain.Image;

public interface ImageService {
    Image findById(int id);

    Image save(Image image);
}
