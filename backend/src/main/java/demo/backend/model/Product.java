package demo.backend.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products", schema = "demo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;

     @Column(name = "product_name")
     private String productName;

     @Column(name = "product_desc")
     private String productDesc;

     @Column(name = "product_price")
     private Long productPrice;

     @Column(name = "product_stock")
     private Integer productStock;

     @CreationTimestamp
     @Column(name = "created_at")
     private Timestamp createdAt;

}
