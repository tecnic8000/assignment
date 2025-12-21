package demo.backend.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(exclude = "customer")
@EqualsAndHashCode(exclude = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "orders", schema = "demo")
public class Order {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "id")
     private UUID id;

     @JdbcTypeCode(SqlTypes.JSON)
     @Column(name = "order_items", columnDefinition = "jsonb")
     private List<OrderItem> orderItems = new ArrayList<>(); 

     @Column(name = "order_status")
     private String orderStatus;

     @Column(name = "payment_total")
     private Long paymentTotal;

     @Column(name = "order_detail")
     private String orderDetail;

     @CreationTimestamp
     @Column(name = "created_at")
     private Timestamp createdAt;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "fk_order_customer"))
     private User customer;

}
