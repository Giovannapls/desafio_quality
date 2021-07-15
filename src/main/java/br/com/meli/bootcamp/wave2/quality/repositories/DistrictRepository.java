package br.com.meli.bootcamp.wave2.quality.repositories;

import br.com.meli.bootcamp.wave2.quality.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByName(String name);
}
