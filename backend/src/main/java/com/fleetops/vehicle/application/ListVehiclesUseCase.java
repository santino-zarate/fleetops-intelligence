package com.fleetops.vehicle.application;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListVehiclesUseCase {

	private final VehicleRepository vehicleRepository;

	public ListVehiclesUseCase(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@Transactional(readOnly = true)
	public List<Vehicle> execute() {
		return vehicleRepository.findAll();
	}
}
