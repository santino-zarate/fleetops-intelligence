package com.fleetops.vehicle.infrastructure.persistence;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleRepositoryAdapter implements VehicleRepository {

	private final SpringDataVehicleRepository springDataVehicleRepository;

	public VehicleRepositoryAdapter(SpringDataVehicleRepository springDataVehicleRepository) {
		this.springDataVehicleRepository = springDataVehicleRepository;
	}

	@Override
	public Vehicle save(Vehicle vehicle) {
		return springDataVehicleRepository.save(JpaVehicleEntity.fromDomain(vehicle)).toDomain();
	}

	@Override
	public Optional<Vehicle> findById(UUID id) {
		return springDataVehicleRepository.findById(id).map(JpaVehicleEntity::toDomain);
	}

	@Override
	public Optional<Vehicle> findByIdentifier(String identifier) {
		return springDataVehicleRepository.findByIdentifier(identifier).map(JpaVehicleEntity::toDomain);
	}

	@Override
	public List<Vehicle> findAll() {
		return springDataVehicleRepository.findAll()
				.stream()
				.map(JpaVehicleEntity::toDomain)
				.toList();
	}
}
