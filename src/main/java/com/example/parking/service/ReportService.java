package com.example.parking.service;
import com.example.parking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;
@Service
public class ReportService {
 private final BookingRepository bookings; private final ZoneId zone=ZoneId.of("Asia/Kolkata");
 public ReportService(BookingRepository b){this.bookings=b;}
 @Transactional(readOnly=true) public BigDecimal revenue(LocalDate date){
  Instant start=date.atStartOfDay(zone).toInstant(), end=date.plusDays(1).atStartOfDay(zone).toInstant();
  BigDecimal result=bookings.sumPaidRevenueBetween(start,end); return result==null?BigDecimal.ZERO.setScale(2):result;
 }
}
