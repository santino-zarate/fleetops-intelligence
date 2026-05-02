package com.fleetops.vehicle.application;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import com.fleetops.vehicle.domain.VehicleStatus;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateVehicleStatusUseCase {

	private final VehicleRepository vehicleRepository;

	public UpdateVehicleStatusUseCase(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Transactional
	public Vehicle execute(UUID id, VehicleStatus status) {
		Vehicle vehicle = vehicleRepository.findById(id)
				.orElseThrow(() -> new VehicleNotFoundException(id));

		vehicle.changeStatus(status);
		return vehicleRepository.save(vehicle);
	}
}
