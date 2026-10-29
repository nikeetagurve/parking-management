package com.example.parking.repository;
import com.example.parking.model.ParkingSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot,Long> {
 List<ParkingSlot> findByOccupiedFalseOrderBySlotCodeAsc();
 List<ParkingSlot> findAllByOrderBySlotCodeAsc();
 boolean existsBySlotCodeIgnoreCase(String slotCode);
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select s from ParkingSlot s where s.id=:id")
 Optional<ParkingSlot> findByIdForUpdate(@Param("id") Long id);
}
