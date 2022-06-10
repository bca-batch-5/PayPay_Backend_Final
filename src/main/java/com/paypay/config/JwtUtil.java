package com.paypay.config;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paypay.dto.Response.Response;
import com.paypay.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private Response response;

    private String JWT_SECRET = "secretKey";

    public Response generateJWT(Authentication authentication) {
        HashMap<String, String> tokens = new HashMap<String, String>();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        // secret di taruh di tempat yang secure seperti enviroment variable
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuedAt(new Date())
                .withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuedAt(new Date())
                .sign(algorithm);
        tokens.put("accessToken", access_token);
        tokens.put("email", user.getUsername());
        response = new Response(HttpStatus.CREATED.value(), "Created Tokens", tokens);
        return response;
    }

    public String getUsernameFromToken(String token) {
        String base64Secret = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
        return Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token)
                .getBody()
                .get("email")
                .toString();
    }

    public boolean validateToken(String token) {
        try {
            String base64Secret = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
            Jwts.parser()
                    .setSigningKey(base64Secret)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return false;
    }
}
