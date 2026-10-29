package com.example.parking.exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.Instant;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 private ResponseEntity<ApiError> response(HttpStatus s,String m,HttpServletRequest r){return ResponseEntity.status(s).body(new ApiError(Instant.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI()));}
 @ExceptionHandler(ResourceNotFoundException.class) public ResponseEntity<ApiError> notFound(ResourceNotFoundException e,HttpServletRequest r){return response(HttpStatus.NOT_FOUND,e.getMessage(),r);}
 @ExceptionHandler(ConflictException.class) public ResponseEntity<ApiError> conflict(ConflictException e,HttpServletRequest r){return response(HttpStatus.CONFLICT,e.getMessage(),r);}
 @ExceptionHandler({BadRequestException.class,IllegalArgumentException.class}) public ResponseEntity<ApiError> badRequest(RuntimeException e,HttpServletRequest r){return response(HttpStatus.BAD_REQUEST,e.getMessage(),r);}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){
  String m=e.getBindingResult().getFieldErrors().stream().map(this::format).distinct().collect(Collectors.joining("; "));
  return response(HttpStatus.BAD_REQUEST,m.isBlank()?"Validation failed":m,r);
 }
 private String format(FieldError e){return e.getField()+": "+e.getDefaultMessage();}
 @ExceptionHandler({MethodArgumentTypeMismatchException.class,HttpMessageNotReadableException.class}) public ResponseEntity<ApiError> malformed(Exception e,HttpServletRequest r){return response(HttpStatus.BAD_REQUEST,"Invalid request value or JSON format",r);}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<ApiError> dataConflict(DataIntegrityViolationException e,HttpServletRequest r){return response(HttpStatus.CONFLICT,"A record with the supplied unique or related value already exists",r);}
 @ExceptionHandler(Exception.class) public ResponseEntity<ApiError> unexpected(Exception e,HttpServletRequest r){return response(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected server error occurred",r);}
}
