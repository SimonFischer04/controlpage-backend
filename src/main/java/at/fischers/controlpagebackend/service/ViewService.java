package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.ViewEntity;

import java.util.List;

public interface ViewService {
    FullView findById(int id);

    List<BasicView> findAllBasic();

    void save(ViewEntity view);

    void save(BasicView view);

    void save(FullView view);
}
