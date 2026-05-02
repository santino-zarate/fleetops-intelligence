package com.fleetops.shared.api;

import com.fleetops.shared.domain.DomainException;
import com.fleetops.vehicle.application.VehicleAlreadyExistsException;
import com.fleetops.vehicle.application.VehicleNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception) {
		List<String> details = exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(this::formatFieldError)
				.toList();

		ApiError error = ApiError.withDetails(
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				"Request validation failed",
				details
		);

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(VehicleNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(VehicleNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiError.of(HttpStatus.NOT_FOUND.value(), "Not Found", exception.getMessage()));
	}

	@ExceptionHandler(VehicleAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleConflict(VehicleAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ApiError.of(HttpStatus.CONFLICT.value(), "Conflict", exception.getMessage()));
	}

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApiError> handleDomain(DomainException exception) {
		return ResponseEntity.badRequest()
				.body(ApiError.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", exception.getMessage()));
	}

	private String formatFieldError(FieldError error) {
		return error.getField() + ": " + error.getDefaultMessage();
	}
}
