package org.example.location;

import org.example.entity.Gym;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LocationRepository extends JpaRepository<Gym, Long> {

    Optional<Gym> getLocationByName(String name);

}
