CREATE TABLE IF NOT EXISTS parking_slots (
 id BIGSERIAL PRIMARY KEY,
 slot_code VARCHAR(20) NOT NULL UNIQUE,
 occupied BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE IF NOT EXISTS bookings (
 id BIGSERIAL PRIMARY KEY,
 vehicle_number VARCHAR(20) NOT NULL,
 slot_id BIGINT NOT NULL REFERENCES parking_slots(id),
 entry_time TIMESTAMP WITH TIME ZONE NOT NULL,
 exit_time TIMESTAMP WITH TIME ZONE,
 amount NUMERIC(12,2),
 payment_status VARCHAR(20) NOT NULL,
 CONSTRAINT chk_booking_payment_status CHECK (payment_status IN ('PENDING','PAID')),
 CONSTRAINT chk_booking_amount CHECK (amount IS NULL OR amount >= 0)
);
CREATE INDEX IF NOT EXISTS idx_bookings_vehicle_active ON bookings (vehicle_number) WHERE exit_time IS NULL;
CREATE INDEX IF NOT EXISTS idx_bookings_exit_time ON bookings (exit_time);
