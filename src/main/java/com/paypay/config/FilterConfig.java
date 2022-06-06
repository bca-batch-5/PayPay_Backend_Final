package com.paypay.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paypay.service.impl.UserDetailsServiceImpl;

@Component
public class FilterConfig extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String headerAuth = request.getHeader("Authorization");
        // check header is null or not
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            String jwtToken = headerAuth.substring(7, headerAuth.length());
            if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
                String email = jwtUtil.getUsernameFromToken(jwtToken);

                // dibentuk user detailsnya
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
                // authentication the user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
    }

    // @Override
    // public Authentication attemptAuthentication(HttpServletRequest request,
    // HttpServletResponse response)
    // throws AuthenticationException {
    // String email = request.getParameter("email");
    // String password = request.getParameter("password");
    // UsernamePasswordAuthenticationToken authenticationToken = new
    // UsernamePasswordAuthenticationToken(email, password);
    // return authenticationManager.authenticate(authenticationToken);
    // }

    // @Override
    // protected void successfulAuthentication(HttpServletRequest request,
    // HttpServletResponse response, FilterChain chain,
    // Authentication authentication) throws IOException, ServletException {
    // UserDetails user = (UserDetails) authentication.getPrincipal();
    // // secret di taruh di tempat yang secure seperti enviroment variable
    // Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    // String access_token = JWT.create()
    // .withSubject(user.getUsername())
    // .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
    // .withIssuer(request.getRequestURI().toString())
    // .withClaim("roles",
    // user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
    // .sign(algorithm);

    // String refresh_token = JWT.create()
    // .withSubject(user.getUsername())
    // .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
    // .withIssuer(request.getRequestURI().toString())
    // .sign(algorithm);
    // response.setHeader("access_token", access_token);
    // response.setHeader("refresh_token", refresh_token);

    // }

}
