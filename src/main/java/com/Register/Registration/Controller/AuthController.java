package com.Register.Registration.Controller;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Register.Registration.AppService.AuthService;
import com.Register.Registration.Request.Register;
import com.Register.Registration.Request.UpdatePassword;
import com.Register.Registration.User.userData;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> Registration(@RequestBody Register register) {
        try {
            String sessionToken = service.Register(register.getuserEmail(), register.getuserName(),
                    register.getuserPassword(), register.getuserRole());
            HashMap<String, String> message = new HashMap<>();
            message.put("Message", "Registation success, Please Vist you account to verify your Email");
            message.put("SessionToken", sessionToken);
            return ResponseEntity.accepted().body(message);
        } catch (Exception e) {
            HashMap<String, String> message = new HashMap<>();
            message.put("Message", e.getCause() + e.getMessage());
            return ResponseEntity.badRequest().body(message);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> ComfimEmail(@RequestParam("token") String token) throws Exception {
        try {
            // Define your frontend base URL at the top
            String frontendRedirectUrl = "/verifymessage.html?message=";

            String result = service.ComfimEmail(token);

            switch (result) {
                case "User Verification token not found":
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(frontendRedirectUrl + "UserNotFound"))
                            .build();

                case "Token already Expire":
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(frontendRedirectUrl + "TokenExpire"))
                            .build();

                case "Success":
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(frontendRedirectUrl + "Success"))
                            .build();

                default:
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(frontendRedirectUrl + "ErrorHappen"))
                            .build();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage() + e.getLocalizedMessage() + e.getCause() + e.getStackTrace());
        }

    }

    @PostMapping("/auth/updatepassword")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody UpdatePassword updatePassword,
            Principal principal) throws Exception {
        if (principal == null) {
            throw new Exception("You must be logged in to change your password.");
        }
        try {

            String result = service.updatePassword(principal.getName(), updatePassword.getcurrentPassword(),
                    updatePassword.getnewPassowrd());
            HashMap<String, String> message = new HashMap<>();
            message.put("Message", "Update Password success!" + result);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            HashMap<String, String> message = new HashMap<>();
            message.put("Message", e.getCause() + "  " + e.getMessage() + "  Person  " + principal.getName());

            return ResponseEntity.badRequest().body(message);
        }

    }

    @GetMapping("/auth/userData")
    public ResponseEntity<?> userData(Authentication authentication) {
        // 1. Safety check: If authentication is null, the user isn't even logged in
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        String email = null;
        Object principal = authentication.getPrincipal();

        // 2. Case A: User logged in via OAuth2 (Google / GitHub)
        if (principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");

            // Fallback for GitHub if email attribute is null but name exists as a handle
            if (email == null || email.isBlank()) {
                email = authentication.getName();
            }
        }
        // 3. Case B: User logged in via regular Form Login / Database /
        // UserDetailsService
        else if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername(); // Typically returns the email or username
        }
        // 4. Case C: Fallback string-based principal
        else {
            email = authentication.getName();
        }

        // 5. Query your service with the safely extracted identity string
        try {
            userData userDatas = service.getuserData(email);
            return ResponseEntity.ok(userDatas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving database record: " + e.getMessage());
        }
    }
}
