package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.entity.View;

public interface ViewService {
    View findById(int id);
    void save(View view);
}
