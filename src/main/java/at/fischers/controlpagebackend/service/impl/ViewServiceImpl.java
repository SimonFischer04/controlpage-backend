package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.repository.ViewRepository;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;

    @Override
    @Transactional
    public FullView findById(int id) {
        ViewEntity v = repository.findById(id).orElse(null);
        if (v == null)
            return null;
        return new FullView(v);
    }

    @Override
    @Transactional
    public List<BasicView> findAllBasic() {
        List<BasicView> viewTOS = new ArrayList<>();
        repository.findAll().forEach(view -> viewTOS.add(new BasicView(view)));
        return viewTOS;
    }

    @Override
    public void save(ViewEntity view) {
        view.getFields().forEach(fieldEntity -> fieldEntity.setView(view));
        repository.save(view);
    }

    @Override
    public void save(BasicView view) {
        save(new ViewEntity(view));
    }

    @Override
    public void save(FullView view) {
        save(new ViewEntity(view));
    }
}
