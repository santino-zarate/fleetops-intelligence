# FleetOps Intelligence

FleetOps Intelligence is a portfolio-oriented fleet operations backend. It will simulate vehicle and machinery telemetry, detect operational issues, generate alerts, open maintenance tickets, and expose dashboard-ready APIs.

The project starts intentionally small: PR #1 builds the fleet core API before adding messaging, telemetry, alerting, analytics, or AI.

## Current Scope

PR #1 includes:

- Spring Boot backend inside `backend/`
- PostgreSQL with Flyway migrations
- `Vehicle` domain model
- REST endpoints for vehicle registration, listing, lookup, and status changes
- Basic validation and error handling
- Unit and API tests
- Docker Compose for local PostgreSQL

## Architecture

```txt
fleetops-intelligence/
  backend/                 Spring Boot API
  docs/                    Living architecture notes
  docker-compose.yml       Local infrastructure
```

The backend uses a lightweight hexagonal structure by domain:

```txt
vehicle/
  domain/                  Business model and repository port
  application/             Use cases
  infrastructure/          JPA adapter
  api/                     REST controller and DTOs
```

## Run Locally

Start PostgreSQL:

```bash
docker-compose up -d
```

Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

The API runs on:

```txt
http://localhost:8080
```

## API

Create a vehicle:

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{"identifier":"TRUCK-001","displayName":"North Route Truck","type":"TRUCK"}'
```

List vehicles:

```bash
curl http://localhost:8080/api/vehicles
```

Change vehicle status:

```bash
curl -X PATCH http://localhost:8080/api/vehicles/<vehicle-id>/status \
  -H "Content-Type: application/json" \
  -d '{"status":"MAINTENANCE"}'
```

## Tests

```bash
cd backend
./mvnw test
```

## Roadmap

- PR #2: simulated telemetry ingestion
- PR #3: alert rules engine
- PR #4: RabbitMQ async processing
- PR #5: idempotency
- PR #6: minimal dashboard
- PR #7: maintenance tickets
- PR #8: basic analytics
- PR #9: risk scoring
- PR #10: AI weekly report
