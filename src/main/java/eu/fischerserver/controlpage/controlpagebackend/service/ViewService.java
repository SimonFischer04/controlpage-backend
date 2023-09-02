package eu.fischerserver.controlpage.controlpagebackend.service;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;

import java.util.List;

public interface ViewService {
    FullView findById(int id);

    List<BasicView> findAllBasic();

    void save(FullView view);
}
