package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.model.entity.StyledTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyledTextRepository extends JpaRepository<StyledTextEntity, Long> {
}
