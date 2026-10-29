package com.example.parking.controller;
import com.example.parking.dto.RevenueResponse;
import com.example.parking.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
@RestController @RequestMapping("/api/reports")
public class ReportController {
 private final ReportService service; public ReportController(ReportService s){service=s;}
 @GetMapping("/revenue") public RevenueResponse revenue(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){return new RevenueResponse(date,service.revenue(date),"INR");}
}
