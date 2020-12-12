package at.fischers.controlpagebackend.service;

import at.fischers.controlpagebackend.dto.BasicView;
import at.fischers.controlpagebackend.dto.FullView;
import at.fischers.controlpagebackend.entity.ViewEntity;

import java.util.List;

public interface ViewService {
    FullView findById(int id);

    List<BasicView> findAllBasic();

    void save(ViewEntity view);
}
