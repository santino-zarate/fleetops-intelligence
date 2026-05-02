package com.fleetops.vehicle.application;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetVehicleUseCase {

	private final VehicleRepository vehicleRepository;

	public GetVehicleUseCase(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Transactional(readOnly = true)
	public Vehicle execute(UUID id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new VehicleNotFoundException(id));
	}
}
