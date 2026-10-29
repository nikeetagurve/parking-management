package com.example.parking.dto;
import com.example.parking.model.ParkingSlot;
public record SlotResponse(Long id,String slotCode,boolean occupied,boolean available) {
 public static SlotResponse from(ParkingSlot s){return new SlotResponse(s.getId(),s.getSlotCode(),s.isOccupied(),!s.isOccupied());}
}
