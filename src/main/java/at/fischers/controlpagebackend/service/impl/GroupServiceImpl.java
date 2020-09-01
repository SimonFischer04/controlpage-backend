package at.fischers.controlpagebackend.service.impl;

import at.fischers.controlpagebackend.entity.Group;
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
        return repository.findById(id).orElse(null);
    }
}
