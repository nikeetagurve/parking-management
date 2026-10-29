package com.example.parking.dto;
import com.example.parking.model.*;
import java.math.BigDecimal;
import java.time.Instant;
public record BookingResponse(Long id,String vehicleNumber,Long slotId,String slotCode,Instant entryTime,Instant exitTime,BigDecimal amount,PaymentStatus paymentStatus,boolean active) {
 public static BookingResponse from(Booking b){return new BookingResponse(b.getId(),b.getVehicleNumber(),b.getSlot().getId(),b.getSlot().getSlotCode(),b.getEntryTime(),b.getExitTime(),b.getAmount(),b.getPaymentStatus(),b.getExitTime()==null);}
}
