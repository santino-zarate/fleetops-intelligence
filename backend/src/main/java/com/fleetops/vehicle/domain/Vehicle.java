package com.fleetops.vehicle.domain;

import com.fleetops.shared.domain.DomainException;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Vehicle {

	private final UUID id;
	private final String identifier;
	private final String displayName;
	private final VehicleType type;
	private VehicleStatus status;
	private final Instant createdAt;
	private Instant updatedAt;

	private Vehicle(
			UUID id,
			String identifier,
			String displayName,
			VehicleType type,
			VehicleStatus status,
			Instant createdAt,
			Instant updatedAt
	) {
		this.id = Objects.requireNonNull(id, "Vehicle id is required");
		this.identifier = requireText(identifier, "Vehicle identifier is required");
		this.displayName = requireText(displayName, "Vehicle display name is required");
		this.type = Objects.requireNonNull(type, "Vehicle type is required");
		this.status = Objects.requireNonNull(status, "Vehicle status is required");
		this.createdAt = Objects.requireNonNull(createdAt, "Vehicle creation timestamp is required");
		this.updatedAt = Objects.requireNonNull(updatedAt, "Vehicle update timestamp is required");
	}

	public static Vehicle create(String identifier, String displayName, VehicleType type) {
		Instant now = Instant.now();
		return new Vehicle(UUID.randomUUID(), identifier, displayName, type, VehicleStatus.ACTIVE, now, now);
	}

	public static Vehicle rehydrate(
			UUID id,
			String identifier,
			String displayName,
			VehicleType type,
			VehicleStatus status,
			Instant createdAt,
			Instant updatedAt
	) {
		return new Vehicle(id, identifier, displayName, type, status, createdAt, updatedAt);
	}

	public void changeStatus(VehicleStatus nextStatus) {
		this.status = Objects.requireNonNull(nextStatus, "Vehicle status is required");
		this.updatedAt = Instant.now();
	}

	public UUID id() {
		return id;
	}

	public String identifier() {
		return identifier;
	}

	public String displayName() {
		return displayName;
	}

	public VehicleType type() {
		return type;
	}

	public VehicleStatus status() {
		return status;
	}

	public Instant createdAt() {
		return createdAt;
	}

	public Instant updatedAt() {
		return updatedAt;
	}

	private static String requireText(String value, String message) {
		if (value == null || value.isBlank()) {
			throw new DomainException(message);
		}
		return value.trim();
	}
}
