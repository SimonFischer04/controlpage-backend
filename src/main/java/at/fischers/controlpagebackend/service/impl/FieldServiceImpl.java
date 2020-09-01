package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.Field;
import at.fischers.controlpagebackend.repository.FieldRepository;
import at.fischers.controlpagebackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository repository;

    @Override
    public Field findById(int id) {
        return repository.findById(id).orElse(null);
    }
}
