package com.fleetops.vehicle.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleRepository;
import com.fleetops.vehicle.domain.VehicleStatus;
import com.fleetops.vehicle.domain.VehicleType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CreateVehicleUseCaseTest {

	private final InMemoryVehicleRepository repository = new InMemoryVehicleRepository();
	private final CreateVehicleUseCase useCase = new CreateVehicleUseCase(repository);

	@Test
	void createsVehicleWhenIdentifierIsUnique() {
		Vehicle vehicle = useCase.execute("TRUCK-001", "North Route Truck", VehicleType.TRUCK);

		assertThat(vehicle.identifier()).isEqualTo("TRUCK-001");
		assertThat(vehicle.status()).isEqualTo(VehicleStatus.ACTIVE);
		assertThat(repository.findAll()).hasSize(1);
	}

	@Test
	void rejectsDuplicatedIdentifier() {
		useCase.execute("TRUCK-001", "North Route Truck", VehicleType.TRUCK);

		assertThatThrownBy(() -> useCase.execute("TRUCK-001", "Backup Truck", VehicleType.TRUCK))
				.isInstanceOf(VehicleAlreadyExistsException.class)
				.hasMessage("Vehicle with identifier 'TRUCK-001' already exists");
	}

	private static class InMemoryVehicleRepository implements VehicleRepository {

		private final List<Vehicle> vehicles = new ArrayList<>();

		@Override
		public Vehicle save(Vehicle vehicle) {
			vehicles.removeIf(existing -> existing.id().equals(vehicle.id()));
			vehicles.add(vehicle);
			return vehicle;
		}

		@Override
		public Optional<Vehicle> findById(UUID id) {
			return vehicles.stream()
					.filter(vehicle -> vehicle.id().equals(id))
					.findFirst();
		}

		@Override
		public Optional<Vehicle> findByIdentifier(String identifier) {
			return vehicles.stream()
					.filter(vehicle -> vehicle.identifier().equals(identifier))
					.findFirst();
		}

		@Override
		public List<Vehicle> findAll() {
			return List.copyOf(vehicles);
		}
	}
}
