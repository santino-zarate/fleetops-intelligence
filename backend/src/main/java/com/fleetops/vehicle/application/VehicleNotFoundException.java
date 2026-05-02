package com.fleetops.vehicle.application;

import java.util.UUID;

public class VehicleNotFoundException extends RuntimeException {

	public VehicleNotFoundException(UUID id) {
		super("Vehicle '" + id + "' was not found");
	}
}
