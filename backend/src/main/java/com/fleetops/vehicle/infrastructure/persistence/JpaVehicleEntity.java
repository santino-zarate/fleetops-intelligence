package com.fleetops.vehicle.infrastructure.persistence;

import com.fleetops.vehicle.domain.Vehicle;
import com.fleetops.vehicle.domain.VehicleStatus;
import com.fleetops.vehicle.domain.VehicleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
public class JpaVehicleEntity {

	@Id
	private UUID id;

	@Column(nullable = false, unique = true, length = 80)
	private String identifier;

	@Column(name = "display_name", nullable = false, length = 120)
	private String displayName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 40)
	private VehicleType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 40)
	private VehicleStatus status;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected JpaVehicleEntity() {
	}

	private JpaVehicleEntity(
			UUID id,
			String identifier,
			String displayName,
			VehicleType type,
			VehicleStatus status,
			Instant createdAt,
			Instant updatedAt
	) {
		this.id = id;
		this.identifier = identifier;
		this.displayName = displayName;
		this.type = type;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static JpaVehicleEntity fromDomain(Vehicle vehicle) {
		return new JpaVehicleEntity(
				vehicle.id(),
				vehicle.identifier(),
				vehicle.displayName(),
				vehicle.type(),
				vehicle.status(),
				vehicle.createdAt(),
				vehicle.updatedAt()
		);
	}

	public Vehicle toDomain() {
		return Vehicle.rehydrate(id, identifier, displayName, type, status, createdAt, updatedAt);
	}
}
