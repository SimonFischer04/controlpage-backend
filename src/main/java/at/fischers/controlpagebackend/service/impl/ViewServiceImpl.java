package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.View;
import at.fischers.controlpagebackend.repository.ViewRepository;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;

    @Override
    public View findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(View view) {
        repository.save(view);
    }
}
