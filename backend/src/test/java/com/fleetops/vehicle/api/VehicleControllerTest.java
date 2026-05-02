package com.fleetops.vehicle.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VehicleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createsAndListsVehicles() throws Exception {
		mockMvc.perform(post("/api/vehicles")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "identifier": "TRUCK-001",
								  "displayName": "North Route Truck",
								  "type": "TRUCK"
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.identifier").value("TRUCK-001"))
				.andExpect(jsonPath("$.status").value("ACTIVE"));

		mockMvc.perform(get("/api/vehicles"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].identifier").value("TRUCK-001"));
	}

	@Test
	void returnsNotFoundWhenVehicleDoesNotExist() throws Exception {
		mockMvc.perform(get("/api/vehicles/00000000-0000-0000-0000-000000000001"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Vehicle '00000000-0000-0000-0000-000000000001' was not found"));
	}

	@Test
	void updatesVehicleStatus() throws Exception {
		MvcResult result = mockMvc.perform(post("/api/vehicles")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "identifier": "MACHINE-001",
								  "displayName": "Excavator 1",
								  "type": "MACHINE"
								}
								"""))
				.andReturn();

		String location = result.getResponse().getHeader("Location");

		mockMvc.perform(patch(location + "/status")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "status": "MAINTENANCE"
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("MAINTENANCE"));
	}
}
