package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.repository.GroupRepository;
import at.fischers.controlpagebackend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;

    @Override
    public Group findById(int id) {
        var groupEntity = repository.findById(id).orElse(null);
        if (groupEntity == null)
            return null;
        return new Group(groupEntity);
    }
}
