package com.fleetops.vehicle.application;

public class VehicleAlreadyExistsException extends RuntimeException {

	public VehicleAlreadyExistsException(String identifier) {
		super("Vehicle with identifier '" + identifier + "' already exists");
	}
}
