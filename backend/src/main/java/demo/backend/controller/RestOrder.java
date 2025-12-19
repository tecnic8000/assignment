package demo.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import demo.backend.model.Order;
import demo.backend.model.OrderItem;
import demo.backend.model.Product;
import demo.backend.model.User;
import demo.backend.security.JwtUtil;
import demo.backend.service.OrderService;
import demo.backend.service.ProductService;
import demo.backend.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/order")
public class RestOrder {

     @Autowired
     JwtUtil jwtUtil;

     private OrderService orderService;
     private ProductService productService;
     private UserService userService;

     public RestOrder(
               OrderService theOrderService,
               ProductService theProdService,
               UserService theUserService) {
          orderService = theOrderService;
          productService = theProdService;
          userService = theUserService;
     }

     // HANDLE NEW ORDER SUBMISSION
     @PostMapping("/create")
     public ResponseEntity<?> createOrder(
               @CookieValue(value = "accessJwt", required = false) String accessCookie,
               @RequestBody Order newOrder) {
          // New Order by Anon
          if (accessCookie == null || accessCookie.isBlank()) {
               Order submitOrder = orderService.create(newOrder, null);
               return ResponseEntity.ok(Map.of("message", submitOrder));
          }

          // jwt validation
          if (!jwtUtil.validateJwtToken(accessCookie)) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Jwt invalid or expired --01"));
          }

          String usernameFromJwt = jwtUtil.getUsernameFromJwtToken(accessCookie);
          Optional<User> jwtUser = userService.findByUsername(usernameFromJwt);
          if (!jwtUser.isPresent()) {
               orderService.create(newOrder, null);
               return ResponseEntity.ok(Map.of("message", "order created but no jwtUser found--01"));
          }

          // New Order by User
          User foundUser = jwtUser.get();
          Order submiteOrder = orderService.create(newOrder, foundUser.getId());

          // DEDUCT STOCK
          List<OrderItem> items = newOrder.getOrderItems();
          for (OrderItem item : items) {
               Product itemProduct = productService.findProductById(item.getItemId());
               productService.updateStock(itemProduct, item.getQuantity());
          }
          return ResponseEntity.ok(Map.of("message", "order created: " + submiteOrder));
     }

     // VIEW ORDERS FOR AUTHENTICATED USER
     @PostMapping("/view")
     public ResponseEntity<?> viewHistory(
               @CookieValue(value = "accessJwt", required = false) String accessCookie) {
          if (accessCookie == null || accessCookie.isBlank()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Jwt empty --02"));
          }

          // JWT VALIDATION
          if (!jwtUtil.validateJwtToken(accessCookie)) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Jwt invalid or expired --03"));
          }
          String usernameFromJwt = jwtUtil.getUsernameFromJwtToken(accessCookie);
          Optional<User> jwtUser = userService.findByUsername(usernameFromJwt);
          if (!jwtUser.isPresent()) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body(Map.of("message", "Jwt user not found --04"));
          }
          User foundUser = jwtUser.get();
          return ResponseEntity.ok(Map.of("message", orderService.findOrdersByCustomerId(foundUser.getId())));
     }

     // VIEW ORDERS FOR ADMIN
     @GetMapping("/viewall")
     public ResponseEntity<?> viewAll(@RequestParam String param) {
          return ResponseEntity.ok(Map.of("message", "order list for admin"));
     }

}
