package com.example.parking.repository;
import com.example.parking.model.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking,Long> {
 List<Booking> findByExitTimeIsNullOrderByEntryTimeAsc();
 boolean existsByVehicleNumberIgnoreCaseAndExitTimeIsNull(String vehicleNumber);
 Optional<Booking> findByIdAndExitTimeIsNull(Long id);
 @Query("select coalesce(sum(b.amount),0) from Booking b where b.paymentStatus=com.example.parking.model.PaymentStatus.PAID and b.exitTime >= :start and b.exitTime < :end")
 BigDecimal sumPaidRevenueBetween(@Param("start") Instant start,@Param("end") Instant end);
}
