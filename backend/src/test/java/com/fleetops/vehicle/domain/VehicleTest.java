package com.fleetops.vehicle.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fleetops.shared.domain.DomainException;
import org.junit.jupiter.api.Test;

class VehicleTest {

	@Test
	void createsActiveVehicleByDefault() {
		Vehicle vehicle = Vehicle.create("TRUCK-001", "North Route Truck", VehicleType.TRUCK);

		assertThat(vehicle.id()).isNotNull();
		assertThat(vehicle.identifier()).isEqualTo("TRUCK-001");
		assertThat(vehicle.displayName()).isEqualTo("North Route Truck");
		assertThat(vehicle.type()).isEqualTo(VehicleType.TRUCK);
		assertThat(vehicle.status()).isEqualTo(VehicleStatus.ACTIVE);
		assertThat(vehicle.createdAt()).isNotNull();
		assertThat(vehicle.updatedAt()).isNotNull();
	}

	@Test
	void rejectsBlankIdentifier() {
		assertThatThrownBy(() -> Vehicle.create(" ", "North Route Truck", VehicleType.TRUCK))
				.isInstanceOf(DomainException.class)
				.hasMessage("Vehicle identifier is required");
	}

	@Test
	void rejectsBlankDisplayName() {
		assertThatThrownBy(() -> Vehicle.create("TRUCK-001", "", VehicleType.TRUCK))
				.isInstanceOf(DomainException.class)
				.hasMessage("Vehicle display name is required");
	}

	@Test
	void changesStatus() {
		Vehicle vehicle = Vehicle.create("TRUCK-001", "North Route Truck", VehicleType.TRUCK);

		vehicle.changeStatus(VehicleStatus.MAINTENANCE);

		assertThat(vehicle.status()).isEqualTo(VehicleStatus.MAINTENANCE);
	}
}
