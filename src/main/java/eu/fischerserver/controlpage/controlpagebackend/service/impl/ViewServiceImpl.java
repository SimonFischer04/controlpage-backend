package eu.fischerserver.controlpage.controlpagebackend.service.impl;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.repository.GroupRepository;
import eu.fischerserver.controlpage.controlpagebackend.repository.ViewRepository;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository repository;
    private final ConversionService conversionService;

    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public FullView findById(int id) {
        ViewEntity v = repository.findById(id).orElse(null);
        if (v == null)
            return null;
        return conversionService.convert(v, FullView.class);
    }

    @Override
    @Transactional
    public List<BasicView> findAllBasic() {
        List<BasicView> viewTOS = new ArrayList<>();
        repository.findAll().forEach(view -> viewTOS.add(conversionService.convert(view, BasicView.class)));
        return viewTOS;
    }

    @Override
    @Transactional
    public void save(FullView view) {
        save(conversionService.convert(view, ViewEntity.class));
    }

    private void save(ViewEntity viewEntity) {
        if (viewEntity == null)
            return;

        viewEntity.getFields().forEach(fieldEntity -> {
//            fieldEntity.setView(viewEntity);
            // client may send f.e. -1 to indicate new field, but database persist requires value of 0 to create new entry
            if (fieldEntity.getId() < 0) {
                fieldEntity.setId(0);
            }
        });

        /*
         fix weird detached merge issue (TODO: better way? :))
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
}
