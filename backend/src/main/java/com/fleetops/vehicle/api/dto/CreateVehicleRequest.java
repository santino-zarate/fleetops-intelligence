package com.fleetops.vehicle.api.dto;

import com.fleetops.vehicle.domain.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateVehicleRequest(
		@NotBlank
		@Size(max = 80)
		String identifier,

		@NotBlank
		@Size(max = 120)
		String displayName,

		@NotNull
		VehicleType type
) {
}
