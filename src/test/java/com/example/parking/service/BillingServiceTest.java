package com.example.parking.service;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
class BillingServiceTest {
 private BillingService billing; private final Instant entry=Instant.parse("2026-10-29T09:00:00Z");
 @BeforeEach void setUp(){billing=new BillingService();}
 @Test void twoHoursFifteenMinutesCostsFifty(){assertEquals(new BigDecimal("50.00"),billing.calculateFee(entry,entry.plusSeconds(8100)));}
 @Test void exactlyThreeHoursCostsFifty(){assertEquals(new BigDecimal("50.00"),billing.calculateFee(entry,entry.plusSeconds(10800)));}
 @Test void threeHoursAndFiveMinutesCostsOneHundred(){assertEquals(new BigDecimal("100.00"),billing.calculateFee(entry,entry.plusSeconds(11100)));}
 @Test void exactlySixHoursCostsOneHundred(){assertEquals(new BigDecimal("100.00"),billing.calculateFee(entry,entry.plusSeconds(21600)));}
 @Test void oneSecondOverThreeHoursCostsOneHundred(){assertEquals(new BigDecimal("100.00"),billing.calculateFee(entry,entry.plusSeconds(10801)));}
 @Test void zeroDurationIsRejected(){assertThrows(IllegalArgumentException.class,()->billing.calculateFee(entry,entry));}
 @Test void negativeDurationIsRejected(){assertThrows(IllegalArgumentException.class,()->billing.calculateFee(entry,entry.minusSeconds(1)));}
 @Test void missingTimestampIsRejected(){assertThrows(IllegalArgumentException.class,()->billing.calculateFee(null,entry));}
}
