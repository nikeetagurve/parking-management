package com.example.parking.service;
import com.example.parking.dto.CreateBookingRequest;
import com.example.parking.exception.*;
import com.example.parking.model.*;
import com.example.parking.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@Service
public class BookingService {
 private final BookingRepository bookings; private final ParkingSlotRepository slots; private final BillingService billing;
 public BookingService(BookingRepository b,ParkingSlotRepository s,BillingService billing){this.bookings=b;this.slots=s;this.billing=billing;}
 @Transactional public Booking book(CreateBookingRequest r){
  String vehicle=r.vehicleNumber().trim().toUpperCase().replaceAll("\\s+","");
  if(bookings.existsByVehicleNumberIgnoreCaseAndExitTimeIsNull(vehicle)) throw new ConflictException("This vehicle already has an active booking");
  ParkingSlot slot=slots.findByIdForUpdate(r.slotId()).orElseThrow(()->new ResourceNotFoundException("Parking slot not found: "+r.slotId()));
  if(slot.isOccupied()) throw new ConflictException("Parking slot is already occupied: "+slot.getSlotCode());
  slot.setOccupied(true);
  return bookings.save(new Booking(vehicle,slot,Instant.now()));
 }
 @Transactional(readOnly=true) public List<Booking> active(){return bookings.findByExitTimeIsNullOrderByEntryTimeAsc();}
 @Transactional(readOnly=true) public Booking get(Long id){return bookings.findById(id).orElseThrow(()->new ResourceNotFoundException("Booking not found: "+id));}
 @Transactional(readOnly=true) public Booking activeBooking(Long id){return bookings.findByIdAndExitTimeIsNull(id).orElseThrow(()->new ResourceNotFoundException("Active booking not found: "+id));}
 @Transactional(readOnly=true) public BigDecimal fare(Long id,Instant at){
  Booking b=activeBooking(id);
  if(at.isBefore(b.getEntryTime())) throw new IllegalArgumentException("Calculation time cannot be before entry time");
  return billing.calculateFee(b.getEntryTime(),at);
 }
 @Transactional public Booking checkout(Long id){
  Booking b=activeBooking(id); Instant exit=Instant.now();
  BigDecimal amount=billing.calculateFee(b.getEntryTime(),exit); b.checkout(exit,amount);
  ParkingSlot slot=slots.findByIdForUpdate(b.getSlot().getId()).orElseThrow(()->new ResourceNotFoundException("Parking slot not found: "+b.getSlot().getId()));
  slot.setOccupied(false); return bookings.save(b);
 }
}
