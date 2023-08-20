package eu.fischerserver.controlpage.controlpagebackend.repository;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Integer> {
}
