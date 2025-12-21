package demo.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.backend.model.User;
import demo.backend.security.JwtUtil;
import demo.backend.service.RedisService;
import demo.backend.service.UserDetailsImpl;
import demo.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class RestUser {

     @Value("${demo.app.httpsecure}")
     boolean httpSecure;

     @Autowired
     AuthenticationManager authenticationManager;

     @Autowired
     JwtUtil jwtUtil;

     private PasswordEncoder passwordEncoder;
     private RedisService redisService;
     private UserService userService;

     public RestUser(
               PasswordEncoder thePasswordEncoder,
               RedisService theRedisService,
               UserService theUserService) {
          passwordEncoder = thePasswordEncoder;
          redisService = theRedisService;
          userService = theUserService;
     }

     @PostMapping("/signup")
     public ResponseEntity<?> signupUser(@RequestBody User signupUser, HttpServletResponse res) {
          // existCheck
          Optional<User> existingUser = userService.findByUsername(signupUser.getUsername());
          if (existingUser.isPresent()) { // 409conflict check
               return ResponseEntity.status(HttpStatus.CONFLICT)
                         .body(Map.of("error", "email already exists, please log in."));
          }
          // newUser
          signupUser.setId(null);
          signupUser.setPassword(passwordEncoder.encode(signupUser.getPassword()));
          User newUser = userService.signup(signupUser);
          return ResponseEntity.status(HttpStatus.CREATED) // 201created
                    .body(Map.of(
                              "message", "Signup successful :)",
                              "user", Map.of("username", newUser.getUsername())));
     }

     @PostMapping("/login")
     public ResponseEntity<?> loginUser(@RequestBody User loginUser, HttpServletResponse res) {
          // existCheck
          Optional<User> existingUser = userService.findByUsername(loginUser.getUsername());
          System.out.println("=== 2. USER FOUND: " + existingUser.isPresent() + " ===");
          if (!existingUser.isPresent()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "invalid email or password"));
          }
          // newLogin
          System.out.println("=== 3. BEFORE AUTHENTICATION ===");
          try {
               Authentication auth = authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
               SecurityContextHolder.getContext().setAuthentication(auth);
               UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
               List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                         .collect(Collectors.toList());
               String role = roles.get(0).replace("ROLE_", "");
               String accessToken = jwtUtil.generateAccessJwt(auth);
               ResponseCookie responseAccessCookie = ResponseCookie.from("accessJwt", accessToken)
                         .httpOnly(true)
                         .secure(httpSecure) // true for HTTPS production
                         .path("/")
                         .maxAge(10 * 60)
                         .sameSite("None")
                         .build();
               res.addHeader("Set-Cookie", responseAccessCookie.toString());
               return ResponseEntity.ok(Map.of(
                         "username", userDetails.getUsername(),
                         "role", role));
          } catch (BadCredentialsException e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("statusText", "invalid email or password"));
          }
     }

     @PostMapping("/loginadmin")
     public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> req, HttpServletResponse res) {
          String password = req.get("password");
          // intentionally hardcoded for a simple demo
          Optional<User> hcAdmin = userService.findByUsername("admin_user");
          User admin = hcAdmin.get();
          System.out.println("ADMIN FOUND");
          try {
               Authentication auth = authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(admin.getUsername(), password));
               SecurityContextHolder.getContext().setAuthentication(auth);
               System.out.println("AUTH PASSED");
               UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
               List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                         .collect(Collectors.toList());
               String role = roles.get(0).replace("ROLE_", "");
               String accessToken = jwtUtil.generateAccessJwt(auth);
               ResponseCookie responseAccessCookie = ResponseCookie.from("accessJwt", accessToken)
                         .httpOnly(true)
                         .secure(httpSecure) // true for HTTPS production
                         .path("/")
                         .maxAge(10 * 60)
                         .sameSite("None")
                         .build();
               res.addHeader("Set-Cookie", responseAccessCookie.toString());
               return ResponseEntity.ok(Map.of(
                         "username", userDetails.getUsername(),
                         "role", role));
          } catch (BadCredentialsException e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("statusText", "CRASHED -00X"));
          }
     }

     @PostMapping("/validate")
     public ResponseEntity<?> validateUser(
               @CookieValue(value = "accessJwt") String accessCookie,
               HttpServletResponse res) {
          if (accessCookie == null || accessCookie.isBlank()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Invalid jwt --01"));
          }
          if (!jwtUtil.validateJwtToken(accessCookie)) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Session is invalid or expired"));

          }

          // check username from token
          String username = jwtUtil.getUsernameFromJwtToken(accessCookie);
          if (username == null || username.isBlank()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Invalid token payload"));
          }

          // Check if token is blacklisted
          if (redisService.isJwtBlacklisted(accessCookie)) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Token is Blacklisted"));
          }

          // initiate new token
          UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
          Authentication auth = new UsernamePasswordAuthenticationToken(
               userDetails, 
               null, 
               userDetails.getAuthorities()
          );
          String newAccessToken = jwtUtil.generateAccessJwt(auth);

          //embed into cookie
          ResponseCookie responseAccessCookie = ResponseCookie.from("accessJwt", newAccessToken)
                    .httpOnly(true)
                    .secure(httpSecure)
                    .path("/")
                    .maxAge(10 * 60)
                    .sameSite("None")
                    .build();
          res.addHeader("Set-Cookie", responseAccessCookie.toString());
          return ResponseEntity.ok(Map.of("message", "session refreshed"));
     }

     @PostMapping("/logout")
     public ResponseEntity<?> logoutUser(
               @CookieValue(value = "accessJwt") String accessCookie,
               HttpServletResponse res) {
          if (accessCookie == null || accessCookie.isBlank()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Invalid jwt --01"));
          }

          if (!jwtUtil.validateJwtToken(accessCookie)) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Invalid or expired refresh token"));
          }

          // blacklist jwt to Redis with TTL
          long expiration = jwtUtil.getExpirationJwt(accessCookie);
          if (expiration > 30) { // dont store it expires in less than said time, let it naturally die
               redisService.blacklistJwt(accessCookie, expiration);
          }

          // clear httpOnly cookie jwt off client's browser
          ResponseCookie clearAccessJwt = ResponseCookie.from("accessJwt", "")
                    .httpOnly(true)
                    .secure(httpSecure)
                    .path("/")
                    .maxAge(0)
                    .sameSite("None")
                    .build();
          res.addHeader("Set-Cookie", clearAccessJwt.toString());
          return ResponseEntity.ok(Map.of("message", "user logged out"));
     }
}
