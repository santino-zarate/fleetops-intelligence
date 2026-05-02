package com.fleetops.vehicle.api.dto;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleStatus;
import com.fleetops.vehicle.domain.VehicleType;
import java.time.Instant;
import java.util.UUID;

public record VehicleResponse(
		UUID id,
		String identifier,
		String displayName,
		VehicleType type,
		VehicleStatus status,
		Instant createdAt,
		Instant updatedAt
) {

	public static VehicleResponse fromDomain(Vehicle vehicle) {
		return new VehicleResponse(
				vehicle.id(),
				vehicle.identifier(),
				vehicle.displayName(),
				vehicle.type(),
				vehicle.status(),
				vehicle.createdAt(),
				vehicle.updatedAt()
		);
	}
}
