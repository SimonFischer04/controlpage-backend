package eu.fischerserver.controlpage.controlpagebackend.repository;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
}
