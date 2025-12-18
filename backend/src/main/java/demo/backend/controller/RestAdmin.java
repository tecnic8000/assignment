package demo.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class RestAdmin {

     // log in
     @GetMapping
     public String login() {
         return "admin";
     }
     
     
     // log out
}
