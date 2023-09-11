package eu.fischerserver.controlpage.controlpagebackend.repository;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyledTextRepository extends JpaRepository<StyledTextEntity, Long> {
}
