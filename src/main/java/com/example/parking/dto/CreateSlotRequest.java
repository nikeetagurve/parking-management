package com.example.parking.dto;
import jakarta.validation.constraints.*;
public record CreateSlotRequest(
 @NotBlank @Size(max=20) @Pattern(regexp="^[A-Za-z0-9-]+$",message="slotCode may contain only letters, numbers, and hyphens") String slotCode
) {}
