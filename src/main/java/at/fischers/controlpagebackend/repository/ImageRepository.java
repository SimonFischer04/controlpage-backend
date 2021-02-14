package at.fischers.controlpagebackend.repository;

import at.fischers.controlpagebackend.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
