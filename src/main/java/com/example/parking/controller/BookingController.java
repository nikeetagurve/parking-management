package com.example.parking.controller;
import com.example.parking.dto.*;
import com.example.parking.model.Booking;
import com.example.parking.service.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
@RestController @RequestMapping("/api/bookings")
public class BookingController {
 private final BookingService service; private final BillingService billing;
 public BookingController(BookingService s,BillingService b){service=s;billing=b;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public BookingResponse book(@Valid @RequestBody CreateBookingRequest r){return BookingResponse.from(service.book(r));}
 @GetMapping("/active") public List<BookingResponse> active(){return service.active().stream().map(BookingResponse::from).toList();}
 @GetMapping("/{id}") public BookingResponse get(@PathVariable Long id){return BookingResponse.from(service.get(id));}
 @GetMapping("/{id}/fare") public FareResponse fare(@PathVariable Long id,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant at){
  Booking b=service.activeBooking(id); Instant calculatedAt=at==null?Instant.now():at;
  var amount=service.fare(id,calculatedAt);
  return new FareResponse(id,b.getEntryTime(),calculatedAt,billing.durationSeconds(b.getEntryTime(),calculatedAt),amount,"INR");
 }
 @PostMapping("/{id}/checkout") public BookingResponse checkout(@PathVariable Long id){return BookingResponse.from(service.checkout(id));}
}
