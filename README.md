# Parking Management REST API — Full Stack-II CA2

Spring Boot 3, Java 17, PostgreSQL, Spring Data JPA, Jakarta Validation, centralized JSON errors, OpenAPI/Swagger, Postman collection, and fee calculation unit tests.

## Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL 14+

## Setup
1. Create a database and user in PostgreSQL:
   ```sql
   CREATE DATABASE parking_db;
   CREATE USER parking_user WITH PASSWORD 'change_me';
   GRANT ALL PRIVILEGES ON DATABASE parking_db TO parking_user;
   ```
   For PostgreSQL 15+, connect to `parking_db` as an administrator and run `GRANT ALL ON SCHEMA public TO parking_user;`.
2. Set environment variables if needed:
   - `DB_URL=jdbc:postgresql://localhost:5432/parking_db`
   - `DB_USERNAME=parking_user`
   - `DB_PASSWORD=change_me`
3. Run `mvn test`, then `mvn spring-boot:run`.
4. Swagger UI: http://localhost:8080/swagger-ui.html
5. Health check: `GET http://localhost:8080/api/health`

The `schema.sql` file initializes tables. Hibernate validates table mappings; existing databases should have the schema applied before starting. Never use schema reset options on data you need to keep.

## Endpoints
| Method | Path | Description |
|---|---|---|
| GET | `/api/slots` | All slots |
| GET | `/api/slots/available` | Available slots |
| POST | `/api/slots` | Create a slot for setup/demo |
| POST | `/api/bookings` | Vehicle entry / allocate slot |
| GET | `/api/bookings/active` | Occupied slots and active bookings |
| GET | `/api/bookings/{id}` | Booking details |
| GET | `/api/bookings/{id}/fare` | Current fare; optional `?at=2026-10-29T12:30:00Z` |
| POST | `/api/bookings/{id}/checkout` | Finalize bill, mark paid, free slot |
| GET | `/api/reports/revenue?date=2026-10-29` | Revenue by checkout date in IST |

## Request examples
Create slot: `POST /api/slots`
```json
{"slotCode":"A-01"}
```
Book vehicle: `POST /api/bookings`
```json
{"vehicleNumber":"MH31AB1234","slotId":1}
```

## Billing
₹50 for each 3-hour block or fraction thereof. The calculation rounds up: 2h15m = ₹50; 3h = ₹50; 3h5m = ₹100. Zero or negative durations are invalid.

## Error contract
JSON response fields: `timestamp`, `status`, `error`, `message`, `path`. Uses 400 for invalid input, 404 for missing resources, and 409 for conflicting state/data.

## Implementation notes
- Slot booking locks the slot row to reduce double-booking races.
- Checkout and slot release are transactional.
- Daily revenue sums bookings marked PAID by checkout time.
- `PAID` is a demonstration status; no real payment gateway is integrated.
