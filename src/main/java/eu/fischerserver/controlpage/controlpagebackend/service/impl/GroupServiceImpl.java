package eu.fischerserver.controlpage.controlpagebackend.service.impl;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.repository.GroupRepository;
import eu.fischerserver.controlpage.controlpagebackend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;
    private final ConversionService conversionService;

    @Override
    public Group findById(int id) {
        var groupEntity = repository.findById(id).orElse(null);
        if (groupEntity == null)
            return null;
        return conversionService.convert(groupEntity, Group.class);
    }
}
