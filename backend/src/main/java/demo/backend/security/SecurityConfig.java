package demo.backend.security;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import demo.backend.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

     @Autowired
     UserService userDetailsService;

     @Autowired
     private AuthEntryPointJwt unauthorizedHandler;

     @Bean
     public AuthTokenFilter authenticationJwtTokenFilter() {
          return new AuthTokenFilter();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
          return authConfig.getAuthenticationManager();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder();
     }

     @Bean
     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
          RedisTemplate<String, Object> template = new RedisTemplate<>();
          template.setConnectionFactory(factory);
          return template;
     }

     @Bean
     public CorsFilter corsFilter(){
          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          CorsConfiguration config = new CorsConfiguration();
          config.addAllowedOrigin("https://localhost:5173");
          config.addAllowedHeader("*");
          config.setAllowCredentials(true);
          source.registerCorsConfiguration("/**", config);
          return new CorsFilter(source);
     }

     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http.cors(Customizer.withDefaults()).authorizeHttpRequests(configurer -> configurer
               .requestMatchers(HttpMethod.GET, "/api/ping").permitAll()
               .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()

               .requestMatchers(HttpMethod.POST, "/api/user/signup").permitAll()
               .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
               .requestMatchers(HttpMethod.POST, "/api/user/validate").permitAll() //TODO system only
               .requestMatchers(HttpMethod.POST, "/api/user/logout").hasRole("customer")

               .requestMatchers(HttpMethod.POST, "/api/product/create").hasRole("admin")
               .requestMatchers(HttpMethod.POST, "/api/product/view").hasRole("customer")
               .requestMatchers(HttpMethod.POST, "/api/product/viewall").hasRole("admin")
               .requestMatchers(HttpMethod.POST, "/api/product/delete").hasRole("admin")

               .requestMatchers(HttpMethod.POST, "/api/order/create").hasRole("customer")
               .requestMatchers(HttpMethod.POST, "/api/order/view").hasRole("customer")
               .requestMatchers(HttpMethod.POST, "/api/order/viewall").hasRole("admin")

          );
          http.csrf(csrf -> csrf.disable())
               .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
          http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
          return http.build();
     }

}
