package com.example.parking.dto;
import jakarta.validation.constraints.*;
public record CreateBookingRequest(
 @NotBlank @Size(max=20) @Pattern(regexp="^[A-Za-z0-9 -]+$",message="vehicleNumber may contain only letters, numbers, spaces, and hyphens") String vehicleNumber,
 @NotNull @Positive Long slotId
) {}
