package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Integer> {
}
