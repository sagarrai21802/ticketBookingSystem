package com.irctc.booking.controller;

import com.irctc.booking.dto.request.BookingRequest;
import com.irctc.booking.dto.response.ApiResponse;
import com.irctc.booking.dto.response.BookingResponse;
import com.irctc.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for booking management
 */
@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Ticket booking APIs")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create booking", description = "Book train tickets")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        BookingResponse response = bookingService.createBooking(request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Booking created successfully. Please complete payment.", response));
    }

    @GetMapping("/pnr-status/{pnr}")
    @Operation(summary = "Check PNR status", description = "Get booking details by PNR number (public)")
    public ResponseEntity<ApiResponse<BookingResponse>> getPnrStatus(@PathVariable String pnr) {
        BookingResponse response = bookingService.getBookingByPnr(pnr);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/my-bookings")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get my bookings", description = "Get all bookings for logged in user")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getMyBookings(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        List<BookingResponse> bookings = bookingService.getUserBookings(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @GetMapping("/upcoming")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get upcoming bookings", description = "Get upcoming journeys for logged in user")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getUpcomingBookings(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        List<BookingResponse> bookings = bookingService.getUpcomingBookings(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(bookings));
    }

    @PostMapping("/{pnr}/cancel")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Cancel booking", description = "Cancel a booking and get refund")
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(
            @PathVariable String pnr,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        BookingResponse response = bookingService.cancelBooking(pnr, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled. Refund will be processed.", response));
    }
}
