package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.DTO.UserDashboardResponse;
import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.UserRepository;
import com.example.travelwing.travelwing.Service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BookingService bookingService;
    private final PdfService pdfService;
    private final RecommendationService recommendationService;
    private final BudgetRecommendationService budgetRecommendationService;
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
            return ResponseEntity.ok(userRepository.findAll());
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }


    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        tokenService.invalidateToken(token);
        return ResponseEntity.noContent().build();
    }



    @CrossOrigin
    @GetMapping("/dashboard")
    public ResponseEntity<UserDashboardResponse> getUserDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        List<Booking> bookings = bookingService.findByUser(Optional.ofNullable(user));
        UserDashboardResponse response = new UserDashboardResponse(user, bookings);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @GetMapping("/dashboard/download/{bookingId}")
    public ResponseEntity<byte[]> downloadBooking(@PathVariable Long bookingId, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Booking booking = bookingService.getBookingById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        byte[] pdfContent = pdfService.generateBookingPdf(booking); // Ensure pdfService.generateBookingPdf(booking) returns byte array
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "booking_" + bookingId + ".pdf");
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User updatedUser) {
        User user = userService.updateUser(userDetails.getUsername(), updatedUser);
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @GetMapping("/recommendations")
    public ResponseEntity<List<String>> getRecommendations(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        List<String> recommendations = recommendationService.getRecommendations(user);
        return ResponseEntity.ok(recommendations);
    }






    @CrossOrigin
    @GetMapping("/budget-recommendations")
    public ResponseEntity<List<String>> getBudgetRecommendations(
            @RequestParam("budget") Double budget, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        List<String> recommendations = budgetRecommendationService.getBudgetRecommendations(budget);
        return ResponseEntity.ok(recommendations);
    }

}

