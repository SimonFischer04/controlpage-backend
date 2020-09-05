package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.View;
import at.fischers.controlpagebackend.repository.ViewRepository;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;

    @Override
    public View findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<View> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(View view) {
        repository.save(view);
    }
}
