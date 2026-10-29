# API examples
Base URL: `http://localhost:8080`

1. `POST /api/slots` body `{"slotCode":"A-01"}`.
2. `POST /api/bookings` body `{"vehicleNumber":"MH31AB1234","slotId":1}`.
3. `GET /api/slots/available`
4. `GET /api/bookings/active`
5. `GET /api/bookings/1/fare`
6. `POST /api/bookings/1/checkout`
7. `GET /api/reports/revenue?date=2026-10-29`

Errors return JSON:
```json
{"timestamp":"2026-10-29T10:00:00Z","status":409,"error":"Conflict","message":"Parking slot is already occupied: A-01","path":"/api/bookings"}
```
