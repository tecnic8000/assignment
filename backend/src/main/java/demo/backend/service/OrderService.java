package demo.backend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import demo.backend.model.Order;
import demo.backend.repository.OrderRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

     @PersistenceContext
     private EntityManager entityManager;

     private UserService userService;
     private OrderRepo orderRepo;

     public OrderService(OrderRepo theOrderRepo, UserService theUserService){
          orderRepo = theOrderRepo;
          userService = theUserService;
     }

     // create order
     @Transactional
     public Order create(Order theOrder, UUID userId) {
          theOrder.setCustomer(userService.findByUUID(userId));
          theOrder.setOrderStatus("pending");

          // DEDUCT PRODUCT STOCK


          return orderRepo.save(theOrder);
     }

     // retrieve order list and details
     public List<Order> findAll(){
          return orderRepo.findAll();
     }

     public List<Order> findOrdersByCustomerId(UUID theId) {
          TypedQuery<Order> query = entityManager.createQuery(
               "SELECT o FROM Order o WHERE o.customerId.id = :data", Order.class);
          query.setParameter("data", theId);
          return query.getResultList();
     }

}
