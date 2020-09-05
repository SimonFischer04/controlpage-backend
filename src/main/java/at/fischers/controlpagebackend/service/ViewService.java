package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.entity.View;

import java.util.List;

public interface ViewService {
    View findById(int id);

    List<View> findAll();

    void save(View view);
}
