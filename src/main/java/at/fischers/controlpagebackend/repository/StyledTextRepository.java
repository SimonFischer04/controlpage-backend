package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.StyledTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyledTextRepository extends JpaRepository<StyledTextEntity, Long> {
}
