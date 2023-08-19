package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.model.domain.Group;
import at.fischers.controlpagebackend.repository.GroupRepository;
import at.fischers.controlpagebackend.service.GroupService;
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
