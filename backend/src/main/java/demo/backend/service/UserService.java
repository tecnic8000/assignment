package demo.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.backend.model.Order;
import demo.backend.model.User;
import demo.backend.repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

     @Autowired
     private EntityManager entityManager;

     private UserRepo userRepo;

     public UserService(UserRepo theUserRepo){
          userRepo = theUserRepo;
     }

     // SIGN UP NEW USER
     @Transactional
     public User signup(User newUser){
          return userRepo.save(newUser); 
     }

     // Auth helpers
     public Optional<User>findByUsername(String username){
          return userRepo.findByUsername(username);
     }

     public User findByUUID(UUID theId){
          Optional<User> result = userRepo.findById(theId);
          User theUser = null;
          if (result.isPresent()){
               theUser = result.get();
          } else {
               throw new RuntimeException("ERR-04-invalidUserId: "+ theId);
          }
          return theUser;
     }

     @Override
     public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userRepo.findByUsername(username)
               .orElseThrow(()-> new UsernameNotFoundException("userDetails usernameNotFound caught:"+username));
          return new UserDetailsImpl(user);
     }

     // USER TO VIEW ORDERS
     public List<Order> findOrdersByUsername(String username){
         TypedQuery<Order> query = entityManager.createQuery(
          "SELECT o FROM Order o JOIN o.customer u WHERE u.username = :data", Order.class); 
          query.setParameter("data", username);
          return query.getResultList();
     }
}
