package com.example.parking.dto;
import java.math.BigDecimal;
import java.time.Instant;
public record FareResponse(Long bookingId,Instant entryTime,Instant calculatedAt,long durationSeconds,BigDecimal amount,String currency) {}
