package com.example.parking.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity
@Table(name="bookings")
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="vehicle_number",nullable=false,length=20) private String vehicleNumber;
 @ManyToOne(fetch=FetchType.EAGER,optional=false) @JoinColumn(name="slot_id",nullable=false) private ParkingSlot slot;
 @Column(name="entry_time",nullable=false) private Instant entryTime;
 @Column(name="exit_time") private Instant exitTime;
 @Column(precision=12,scale=2) private BigDecimal amount;
 @Enumerated(EnumType.STRING) @Column(name="payment_status",nullable=false,length=20) private PaymentStatus paymentStatus=PaymentStatus.PENDING;
 protected Booking() {}
 public Booking(String vehicleNumber,ParkingSlot slot,Instant entryTime){this.vehicleNumber=vehicleNumber;this.slot=slot;this.entryTime=entryTime;}
 public Long getId(){return id;} public String getVehicleNumber(){return vehicleNumber;} public ParkingSlot getSlot(){return slot;}
 public Instant getEntryTime(){return entryTime;} public Instant getExitTime(){return exitTime;} public BigDecimal getAmount(){return amount;}
 public PaymentStatus getPaymentStatus(){return paymentStatus;}
 public void checkout(Instant exitTime,BigDecimal amount){this.exitTime=exitTime;this.amount=amount;this.paymentStatus=PaymentStatus.PAID;}
}
