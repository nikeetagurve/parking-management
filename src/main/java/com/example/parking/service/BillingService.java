package com.example.parking.service;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
@Service
public class BillingService {
 private static final BigDecimal RATE=BigDecimal.valueOf(50);
 private static final long BLOCK_SECONDS=Duration.ofHours(3).toSeconds();
 public BigDecimal calculateFee(Instant entry,Instant exit){
  if(entry==null||exit==null) throw new IllegalArgumentException("Entry and exit timestamps are required");
  if(!exit.isAfter(entry)) throw new IllegalArgumentException("Exit time must be after entry time");
  Duration duration=Duration.between(entry,exit);
  long seconds=duration.getSeconds();
  if(duration.getNano()>0) seconds++;
  long blocks=(seconds+BLOCK_SECONDS-1)/BLOCK_SECONDS;
  return RATE.multiply(BigDecimal.valueOf(blocks)).setScale(2,RoundingMode.UNNECESSARY);
 }
 public long durationSeconds(Instant entry,Instant exit){
  if(entry==null||exit==null||exit.isBefore(entry)) throw new IllegalArgumentException("A valid entry and calculation timestamp are required");
  return Duration.between(entry,exit).getSeconds();
 }
}
