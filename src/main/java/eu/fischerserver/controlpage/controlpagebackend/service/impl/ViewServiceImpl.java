package eu.fischerserver.controlpage.controlpagebackend.service.impl;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.repository.ActionRepository;
import eu.fischerserver.controlpage.controlpagebackend.repository.GroupRepository;
import eu.fischerserver.controlpage.controlpagebackend.repository.ViewRepository;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
    private final ActionRepository actionRepository;

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

    private boolean isStoredWithOtherType(ActionEntity action) {
        if (action.getId() <= 0) {
            return false;
        }
        return actionRepository.findById(action.getId())
                .map(stored -> Hibernate.getClass(stored) != action.getClass())
                .orElse(false);
    }

    private void save(ViewEntity viewEntity) {
        if (viewEntity == null)
            return;

        viewEntity.getFields().forEach(fieldEntity -> {
            // client may send f.e. id -1 to indicate new entity(field, styleText, ...), but database persist requires value of 0 to create new entry
            if (fieldEntity.getId() < 0) {
                fieldEntity.setId(0);
            }
            if (fieldEntity.getTitle() != null && fieldEntity.getTitle().getId() < 0) {
                fieldEntity.getTitle().setId(0);
            }
            if (fieldEntity.getAction() != null && fieldEntity.getAction().getId() < 0) {
                fieldEntity.getAction().setId(0);
            }
            // The editor keeps the id when the type of an action is changed. An action of another type is a new entity
            // (another table): save it as new one, orphan removal deletes the old one.
            if (fieldEntity.getAction() != null && isStoredWithOtherType(fieldEntity.getAction())) {
                fieldEntity.getAction().setId(0);
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
