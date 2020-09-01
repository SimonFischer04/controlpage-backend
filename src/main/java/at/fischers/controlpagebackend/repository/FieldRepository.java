package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {
}
