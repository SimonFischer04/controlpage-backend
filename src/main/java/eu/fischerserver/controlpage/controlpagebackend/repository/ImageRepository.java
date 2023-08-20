package eu.fischerserver.controlpage.controlpagebackend.repository;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
