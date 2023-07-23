package com.gitcolab.services;

import com.gitcolab.dto.*;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.exceptions.TokenRefreshException;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.security.jwt.JwtUtils;
import com.gitcolab.utilities.EmailSender;
import com.gitcolab.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    RefreshTokenService refreshTokenService;
    @Autowired
    EmailSender emailSender;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService, EmailSender emailSender) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.emailSender = emailSender;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtTokenResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail()));
    }

    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
    public  ResponseEntity<?> registerUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByUsername(registerUserRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(registerUserRequest.getUsername()
                ,registerUserRequest.getEmail()
                ,encoder.encode(registerUserRequest.getPassword())
                ,registerUserRequest.getFirstName()
                ,registerUserRequest.getLastName());

        userRepository.save(user);

        boolean sendEmail = emailSender.sendEmail(registerUserRequest.getEmail(),
                "Welcome to GitColab",
                "Hello " + user.getFirstName() + ",\n\nWelcome to GitColab! Happy integrating things! \n\nTeam GitColab!");

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> sendResetPasswordOTP(SendOTPRequest sendOTPRequest) {
        if (!userRepository.existsByEmail(sendOTPRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not exist in system!"));
        }

        Optional<User> user = userRepository.getUserByEmail(sendOTPRequest.getEmail());
        if(!user.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Something went wrong!"));
        }
        String otpExpiry = String.valueOf(Instant.now().plusSeconds(300).getEpochSecond());
        String otp = HelperUtils.generateOTP(6);
        user.get().setOtp(otp);
        user.get().setOtpExpiry(otpExpiry);

        boolean sendEmail = emailSender.sendEmail(sendOTPRequest.getEmail(),
                "Password reset request | GitColab",
                "Hello " + user.get().getFirstName() + ",\n\nVerification Code for reset password request: " + otp + "\n\nThank you,\nGitColab");

        userRepository.update(user.get());
        if(!sendEmail) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Something went wrong!"));
        }
        return ResponseEntity.ok(new MessageResponse("Verification Code sent successfully!"));
    }

    public ResponseEntity<?> validateResetPasswordOTP(ValidateOTPRequest validateOTPRequest) {
        if (!userRepository.existsByEmail(validateOTPRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not exist in system!"));
        }
        Optional<User> user = userRepository.getUserByEmail(validateOTPRequest.getEmail());
        if(!user.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Something went wrong!"));
        }
        boolean isValidOTP =user.get().getOtp().equals(validateOTPRequest.getOtp());
        long currentEpoch = Instant.now().getEpochSecond();
        boolean isValidOTPExpiry = Long.parseLong(user.get().getOtpExpiry()) >= currentEpoch;
        if(!isValidOTPExpiry) return ResponseEntity.badRequest().body(new MessageResponse("Error: OTP expired! Please resend."));
        if(!isValidOTP)
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid OTP! Please try again"));

        user.get().setOtp(null);
        user.get().setOtpExpiry(null);
        userRepository.update(user.get());
        return ResponseEntity.ok(new MessageResponse("OTP verified successfully!"));
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!userRepository.existsByEmail(resetPasswordRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not exist in system!"));

        Optional<User> user = userRepository.getUserByEmail(resetPasswordRequest.getEmail());
        if(!user.isPresent())
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Something went wrong!"));

        if(!resetPasswordRequest.getPassword().equals(resetPasswordRequest.getConfirmPassword()))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password and Confirm Password are not same!"));

        user.get().setPassword(encoder.encode(resetPasswordRequest.getPassword()));
        userRepository.update(user.get());
        return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
    }

}
