package eu.fischerserver.controlpage.controlpagebackend.service;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;

public interface ImageService {
    Image findById(int id);

    Image save(Image image);
}
