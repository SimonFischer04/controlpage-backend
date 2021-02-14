package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.dto.Image;

public interface ImageService {
    Image findById(int id);

    Image save(Image image);
}
