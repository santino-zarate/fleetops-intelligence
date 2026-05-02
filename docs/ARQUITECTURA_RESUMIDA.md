# Arquitectura Resumida

FleetOps Intelligence arranca como un monolito modular en Java/Spring Boot.

## Decision actual

PR #1 crea el modulo `vehicle`, que representa el nucleo minimo de flota. El sistema permite registrar, consultar y cambiar el estado operativo de vehiculos o maquinaria.

## Estructura

```txt
backend/
  src/main/java/com/fleetops/
    shared/
    vehicle/
      domain/
      application/
      infrastructure/
      api/
```

## Por que monolito modular

El objetivo del proyecto es demostrar criterio backend, no distribuir complejidad antes de tener un dominio real. Separar por modulos permite crecer hacia telemetria, alertas, tickets y analytics sin introducir microservicios prematuros.

## Stack actual

- Java 21
- Spring Boot
- PostgreSQL
- Flyway
- JPA/Hibernate
- JUnit

## Fuera de alcance en PR #1

- RabbitMQ
- Telemetria
- Alertas
- Tickets de mantenimiento
- Dashboard
- Auth
- Multi-tenant
- IA/reportes
