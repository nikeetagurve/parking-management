package com.example.parking.controller;
import com.example.parking.dto.*;
import com.example.parking.service.SlotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/slots")
public class SlotController {
 private final SlotService service; public SlotController(SlotService s){service=s;}
 @GetMapping public List<SlotResponse> all(){return service.all().stream().map(SlotResponse::from).toList();}
 @GetMapping("/available") public List<SlotResponse> available(){return service.available().stream().map(SlotResponse::from).toList();}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public SlotResponse create(@Valid @RequestBody CreateSlotRequest r){return SlotResponse.from(service.create(r));}
}
