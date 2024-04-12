package com.example.version1.requests;

import com.example.version1.users.User;
import com.example.version1.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/requests")
@RequiredArgsConstructor
public class ApiRequestController {
    private final RequestService requestService;
    private final UserRepository userRepository; // Inject UserRepository

    @GetMapping
    public ResponseEntity<List<Request>> getAll() {
        List<Request> requests = requestService.getAllRequest();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);

//CHANGED
@PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request, Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof Jwt)) {
            throw new AuthenticationException("Authentication is missing or invalid for this request");
        }

        Jwt jwtToken = (Jwt) authentication.getPrincipal();

        // Extract user email (sub) from JWT claims
        String userEmail = jwtToken.getClaim("sub");

        // Find user by email in your database
    Optional<User> userOptional = userRepository.findByEmail(userEmail);
    if (userOptional.isEmpty()) {
        throw new AuthenticationException("User not found");
    }
    User user = userOptional.get(); // Extract User from Optional

    Long userId = user.getId(); // Get the user ID from the retrieved user object

    request.setUserId(userId);// Set user ID in the request object

        Request createdRequest = requestService.createRequest(request, userId);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<Request>> getMyRequests(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }

        // Extract user email from JWT claims
        Jwt jwtToken = (Jwt) authentication.getPrincipal();
        String userEmail = jwtToken.getClaim("sub");

        // Find user by email in your database
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        User user = userOptional.get();

        // Extract user ID
        Long userId = user.getId();

        // Retrieve requests created by the user
        List<Request> userRequests = requestService.getRequestsByUserId(userId);

        return new ResponseEntity<>(userRequests, HttpStatus.OK);
    }


    @GetMapping("/Hr_req")
    public ResponseEntity<List<Request>> getRequestsRH(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }
        List<String> allowedRequestTypes = Arrays.asList("Loan", "P_situation", "Document", "Job Transfer");
        List<Request> filteredRequests = requestService.getRequestsByTypes(allowedRequestTypes);
        return new ResponseEntity<>(filteredRequests, HttpStatus.OK);
    }

    @GetMapping("/Admin_req")
    public ResponseEntity<List<Request>> getRequestsadmin(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }
        List<String> allowedRequestTypes = Arrays.asList("Auth","Leave");
        List<Request> filteredRequests = requestService.getRequestsByTypes(allowedRequestTypes);
        return new ResponseEntity<>(filteredRequests, HttpStatus.OK);
    }

    @GetMapping("/Admin-pending-requests")
    public ResponseEntity<List<Request>> getPendingRequestsForAdmin(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }

        List<String> allowedRequestTypes = Arrays.asList("Auth", "Leave");
        List<Request> filteredRequests = requestService.getRequestsByTypes(allowedRequestTypes);

        // Further filter by "Pending" status
        List<Request> pendingRequests = filteredRequests.stream()
                .filter(request -> "Pending".equals(request.getStatue())) // Check for null and then compare
                .collect(Collectors.toList());


        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @GetMapping("/RH-pending-requests")
    public ResponseEntity<List<Request>> getPendingRequestsForRH(Authentication authentication) throws AuthenticationException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }

        List<String> allowedRequestTypes = Arrays.asList("Loan", "P_situation", "Document", "Job Transfer");
        List<Request> filteredRequests = requestService.getRequestsByTypes(allowedRequestTypes);

        // Further filter by "Pending" status
        List<Request> pendingRequests = filteredRequests.stream()
                .filter(request -> "Pending".equals(request.getStatue())) // Check for null and then compare
                .collect(Collectors.toList());


        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @PutMapping("/{id}/statue")
    public ResponseEntity<Request> updateRequestStatue(@PathVariable Long id, @RequestParam String newStatue) {
        Request updatedRequest = requestService.updateRequestStatue(id, newStatue);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }


    // Helper method to extract user ID from JWT claims
    private Long getUserIdFromAuthentication(Authentication authentication) {
        Jwt jwtToken = (Jwt) authentication.getPrincipal();
        return jwtToken.getClaim("id"); // Assuming "id" is the claim name for user ID
    }


}



