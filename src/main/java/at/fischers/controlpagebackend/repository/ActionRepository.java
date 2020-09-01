package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
}
