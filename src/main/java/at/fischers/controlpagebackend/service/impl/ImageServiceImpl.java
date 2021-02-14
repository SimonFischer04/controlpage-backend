package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.repository.ImageRepository;
import at.fischers.controlpagebackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;

    @Override
    public Image findById(int id) {
        ImageEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return new Image(entity);
    }

    @Override
    public Image save(Image image) {
        return new Image(repository.save(new ImageEntity(image)));
    }
}
