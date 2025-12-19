package demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import demo.backend.model.Product;
import demo.backend.repository.ProductRepo;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

     private ProductRepo productRepo;

     public ProductService(ProductRepo theProductRepo){
          productRepo = theProductRepo;
     } 

     // CREATE A PRODUCT
     @Transactional
     public Product create(Product newProduct){
          return productRepo.save(newProduct);
     }


     // RETRIEVE PRODUCT LIST AND DETAILS 
     public List<Product> findAll() {
          return productRepo.findAll();
     }

     public Product findProductById(Integer theId){
          Optional<Product> orderProduct = productRepo.findById(theId);
          if (!orderProduct.isPresent()){
               throw new RuntimeException("no ProductId found --"+theId);
          }
          return  orderProduct.get();
     }

     // UPDATE STOCK: DEDUCT AND AUTO-DELETE IF 0
     public void updateStock(Product theProduct, Integer deductAmount){
          Integer currentStock = theProduct.getProductStock();
          Integer newStock = currentStock - deductAmount;
          boolean soldOut = newStock == 0;
          if (soldOut) {
               productRepo.deleteById(theProduct.getId());
          } else {
               theProduct.setProductStock(newStock);
          }
     }

     // DELETE A PRODUCT
     @Transactional
     public void delete(Integer theProductId){
          productRepo.deleteById(theProductId);
     }
}
