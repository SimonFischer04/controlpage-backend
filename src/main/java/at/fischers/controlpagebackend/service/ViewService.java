package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.model.domain.view.BasicView;
import at.fischers.controlpagebackend.model.domain.view.FullView;
import at.fischers.controlpagebackend.model.domain.view.FullViewDTO;
import at.fischers.controlpagebackend.model.entity.ViewEntity;

import java.util.List;

public interface ViewService {
    FullView findById(int id);

    List<BasicView> findAllBasic();

    void save(ViewEntity view);

//    void save(BasicView view);

    void save(FullView view);

    void save(FullViewDTO fullViewDTO);
}
