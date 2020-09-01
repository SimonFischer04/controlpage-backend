package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.entity.action.Action;

public interface ActionService {
    Action findById(int id);
}
