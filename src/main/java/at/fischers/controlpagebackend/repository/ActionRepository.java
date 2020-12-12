package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.action.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Integer> {
}
