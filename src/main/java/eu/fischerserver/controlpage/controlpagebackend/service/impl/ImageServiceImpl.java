package eu.fischerserver.controlpage.controlpagebackend.service.impl;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ImageEntity;
import eu.fischerserver.controlpage.controlpagebackend.repository.ImageRepository;
import eu.fischerserver.controlpage.controlpagebackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    private final ConversionService conversionService;

    @Override
    public Image findById(int id) {
        ImageEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return conversionService.convert(entity, Image.class);
    }

    @Override
    public Image save(Image image) {
        var entity = conversionService.convert(image, ImageEntity.class);
        if (entity == null) {
            return null;
        }
        return conversionService.convert(entity, Image.class);
    }
}
