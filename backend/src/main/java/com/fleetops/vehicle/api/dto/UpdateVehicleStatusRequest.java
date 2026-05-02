package com.fleetops.vehicle.api.dto;

import com.fleetops.vehicle.domain.VehicleStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateVehicleStatusRequest(
		@NotNull
		VehicleStatus status
) {
}
