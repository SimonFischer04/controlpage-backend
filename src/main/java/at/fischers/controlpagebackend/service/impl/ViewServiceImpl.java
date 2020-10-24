package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.View;
import at.fischers.controlpagebackend.repository.ViewRepository;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;

    @Override
    public View findById(int id) {
        View v = repository.findById(id).orElse(null);
        if(v == null)
            return null;
        Hibernate.initialize(v.getFields());
        return v;
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
