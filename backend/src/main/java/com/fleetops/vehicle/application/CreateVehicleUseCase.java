package com.fleetops.vehicle.application;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import com.fleetops.vehicle.domain.VehicleType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateVehicleUseCase {

	private final VehicleRepository vehicleRepository;

	public CreateVehicleUseCase(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Transactional
	public Vehicle execute(String identifier, String displayName, VehicleType type) {
		vehicleRepository.findByIdentifier(identifier)
				.ifPresent(existing -> {
					throw new VehicleAlreadyExistsException(identifier);
				});

		Vehicle vehicle = Vehicle.create(identifier, displayName, type);
		return vehicleRepository.save(vehicle);
	}
}
