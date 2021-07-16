package br.com.meli.bootcamp.wave2.quality.repositories;

import br.com.meli.bootcamp.wave2.quality.entities.District;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {

  Optional<District> findByName(String name);

  boolean existsByName(String name);

}
