package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.action.Action;
import at.fischers.controlpagebackend.repository.ActionRepository;
import at.fischers.controlpagebackend.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository repository;

    @Override
    public Action findById(int id) {
        return repository.findById(id).orElse(null);
    }
}
