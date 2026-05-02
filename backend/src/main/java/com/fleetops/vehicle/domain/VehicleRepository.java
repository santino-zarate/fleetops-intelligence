package com.fleetops.vehicle.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository {

	Vehicle save(Vehicle vehicle);

	Optional<Vehicle> findById(UUID id);

	Optional<Vehicle> findByIdentifier(String identifier);

	List<Vehicle> findAll();
}
