package demo.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.backend.model.Product;
import demo.backend.service.ProductService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/product")
public class RestProduct {

     private ProductService productService;

     public RestProduct(ProductService theProductService) {
          productService = theProductService;
     }

     // CREATE A PRODUCT
     @PostMapping("/create")
     public ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
         newProduct.setId(null);
         Product submiProduct = productService.create(newProduct);
         return ResponseEntity.ok(Map.of("message", submiProduct));
     }
     

     // RETRIEVE PRODUCT LIST AND DETAILS
     @GetMapping("/view")
     public ResponseEntity<?> getProducts() {
          List<Product> productList = productService.findAll();
          return ResponseEntity.ok(Map.of("message", productList));
     }
     // DELETE A PRODUCT
     @DeleteMapping("/delete/{id}")
     public ResponseEntity<?> deleteProduct(@PathVariable Integer id ){
          productService.delete(id);
          return ResponseEntity.ok(Map.of("message","product"+id+"DELETED"));
     }
}
