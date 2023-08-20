package eu.fischerserver.controlpage.controlpagebackend.service;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;

public interface GroupService {
    Group findById(int id);
}
