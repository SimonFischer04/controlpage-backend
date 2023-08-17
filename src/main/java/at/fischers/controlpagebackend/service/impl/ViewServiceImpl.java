package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.dto.view.FullViewDTO;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.repository.GroupRepository;
import at.fischers.controlpagebackend.repository.ViewRepository;
import at.fischers.controlpagebackend.service.GroupService;
import at.fischers.controlpagebackend.service.ImageService;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;
    private final ImageService imageService;
    private final GroupService groupService;

    private final GroupRepository groupRepository;

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
    @Transactional
    public void save(ViewEntity viewEntity) {
        viewEntity.getFields().forEach(fieldEntity -> {
            fieldEntity.setView(viewEntity);
            // client may send f.e. -1 to indicate new field, but database persist requires value of 0 to create new entry
            if(fieldEntity.getId() < 0){
                fieldEntity.setId(0);
            }
        });

        /*
         fix weird detached merge issue
         */
        var actualGroup = viewEntity.getGroup();
        viewEntity.setGroup(null);
        var saved = repository.save(viewEntity);

        if (actualGroup != null) {
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            var temp = repository.findById(viewEntity.getId()).get();
            temp.setGroup(groupRepository.findById(actualGroup.getId()).orElse(null));
            repository.save(temp);
        }
    }

    @Override
    @Transactional
    public void save(BasicView view) {
        save(new ViewEntity(imageService, view));
    }

    @Override
    @Transactional
    public void save(FullView view) {
        save(new ViewEntity(imageService, view));
    }

    @Override
    @Transactional
    public void save(FullViewDTO fullViewDTO) {
        save(new FullView(groupService, fullViewDTO));
    }
}
