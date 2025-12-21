package demo.backend.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userDetailsImpl;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String path = request.getRequestURI();
    // for public endpoints
    if (path.equals("/api/user/login") ||
        path.equals("/api/user/signup") || 
        path.equals("/api/user/validate") ||
        path.equals("/api/product/view") || 
        path.equals("/api/user/loginadmin") || 
        path.equals("/api/ping")) {
      logger.info("Bypassing JWT filter for public endpoint: {}", path);
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
        String username = jwtUtil.getUsernameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsImpl.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user auth - AuthTokenFilter-01: {}", e);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    // First try to get JWT from cookie
    String jwt = getJwtFromCookie(request);
    if (jwt != null) {
      return jwt;
    }

    // Fallback to Authorization header
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }
    return null;
  }

  private String getJwtFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("accessJwt".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
