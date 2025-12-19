package demo.backend.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "demo")
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id")
     private UUID id;
     
     @Column(name = "username")
     private String username;

     @Column(name = "password")
     private String password;

     @Column(name = "role")
     private String role;

     @CreationTimestamp
     @Column(name = "created_at")
     private Timestamp createdAt;

     @JsonIgnore
     @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
     private List<Order> orders;
     
}
