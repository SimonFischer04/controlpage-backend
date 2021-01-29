package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.entity.FieldEntity;

public interface FieldService {
    void save(FieldEntity fieldEntity);
    void save(Field field);
}
