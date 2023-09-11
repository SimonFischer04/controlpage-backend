package eu.fischerserver.controlpage.controlpagebackend.repository;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, Integer> {
}
