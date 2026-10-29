package com.example.parking.model;
import jakarta.persistence.*;
@Entity
@Table(name="parking_slots")
public class ParkingSlot {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="slot_code", nullable=false, unique=true, length=20) private String slotCode;
 @Column(nullable=false) private boolean occupied=false;
 protected ParkingSlot() {}
 public ParkingSlot(String slotCode) { this.slotCode=slotCode; }
 public Long getId(){return id;} public String getSlotCode(){return slotCode;} public boolean isOccupied(){return occupied;}
 public void setSlotCode(String slotCode){this.slotCode=slotCode;} public void setOccupied(boolean occupied){this.occupied=occupied;}
}
