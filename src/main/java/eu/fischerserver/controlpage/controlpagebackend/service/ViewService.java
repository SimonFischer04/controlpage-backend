package eu.fischerserver.controlpage.controlpagebackend.service;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullViewDTO;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;

import java.util.List;

public interface ViewService {
    FullView findById(int id);

    List<BasicView> findAllBasic();

    void save(ViewEntity view);

//    void save(BasicView view);

    void save(FullView view);

    void save(FullViewDTO fullViewDTO);
}
