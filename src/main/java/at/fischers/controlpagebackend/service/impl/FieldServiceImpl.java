package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.repository.FieldRepository;
import at.fischers.controlpagebackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository repository;

    @Override
    public void save(FieldEntity fieldEntity) {
        repository.save(fieldEntity);
    }

    @Override
    public void save(Field field) {
        save(new FieldEntity(field));
    }
}
