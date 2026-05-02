package com.fleetops.vehicle.api;

import com.fleetops.vehicle.api.dto.CreateVehicleRequest;
import com.fleetops.vehicle.api.dto.UpdateVehicleStatusRequest;
import com.fleetops.vehicle.api.dto.VehicleResponse;
import com.fleetops.vehicle.application.CreateVehicleUseCase;
import com.fleetops.vehicle.application.GetVehicleUseCase;
import com.fleetops.vehicle.application.ListVehiclesUseCase;
import com.fleetops.vehicle.application.UpdateVehicleStatusUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

	private final CreateVehicleUseCase createVehicle;
	private final GetVehicleUseCase getVehicle;
	private final ListVehiclesUseCase listVehicles;
	private final UpdateVehicleStatusUseCase updateVehicleStatus;

	public VehicleController(
			CreateVehicleUseCase createVehicle,
			GetVehicleUseCase getVehicle,
			ListVehiclesUseCase listVehicles,
			UpdateVehicleStatusUseCase updateVehicleStatus
	) {
		this.createVehicle = createVehicle;
		this.getVehicle = getVehicle;
		this.listVehicles = listVehicles;
		this.updateVehicleStatus = updateVehicleStatus;
	}

	@PostMapping
	public ResponseEntity<VehicleResponse> create(@Valid @RequestBody CreateVehicleRequest request) {
		VehicleResponse response = VehicleResponse.fromDomain(
				createVehicle.execute(request.identifier(), request.displayName(), request.type())
		);

		return ResponseEntity.created(URI.create("/api/vehicles/" + response.id())).body(response);
	}

	@GetMapping
	public List<VehicleResponse> list() {
		return listVehicles.execute()
				.stream()
				.map(VehicleResponse::fromDomain)
				.toList();
	}

	@GetMapping("/{id}")
	public VehicleResponse getById(@PathVariable UUID id) {
		return VehicleResponse.fromDomain(getVehicle.execute(id));
	}

	@PatchMapping("/{id}/status")
	public VehicleResponse updateStatus(
			@PathVariable UUID id,
			@Valid @RequestBody UpdateVehicleStatusRequest request
	) {
		return VehicleResponse.fromDomain(updateVehicleStatus.execute(id, request.status()));
	}
}
