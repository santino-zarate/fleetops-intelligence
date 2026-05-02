package com.fleetops.vehicle.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataVehicleRepository extends JpaRepository<JpaVehicleEntity, UUID> {

	Optional<JpaVehicleEntity> findByIdentifier(String identifier);
}
