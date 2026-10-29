package com.example.parking.service;
import com.example.parking.dto.CreateSlotRequest;
import com.example.parking.exception.*;
import com.example.parking.model.ParkingSlot;
import com.example.parking.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class SlotService {
 private final ParkingSlotRepository slots;
 public SlotService(ParkingSlotRepository slots){this.slots=slots;}
 @Transactional(readOnly=true) public List<ParkingSlot> all(){return slots.findAllByOrderBySlotCodeAsc();}
 @Transactional(readOnly=true) public List<ParkingSlot> available(){return slots.findByOccupiedFalseOrderBySlotCodeAsc();}
 @Transactional public ParkingSlot create(CreateSlotRequest r){
  String code=r.slotCode().trim().toUpperCase();
  if(slots.existsBySlotCodeIgnoreCase(code)) throw new ConflictException("Slot code already exists: "+code);
  return slots.save(new ParkingSlot(code));
 }
 @Transactional(readOnly=true) public ParkingSlot get(Long id){return slots.findById(id).orElseThrow(()->new ResourceNotFoundException("Parking slot not found: "+id));}
}
