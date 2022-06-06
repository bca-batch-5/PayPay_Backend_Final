package com.paypay.config;

import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paypay.dto.Response.Response;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private Response response;

    public Response generateJWT(Authentication authentication) {
        HashMap<String, String> tokens = new HashMap<String, String>();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        // secret di taruh di tempat yang secure seperti enviroment variable
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
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
        tokens.put("refreshToken", refresh_token);
        response = new Response(HttpStatus.CREATED.value(), "Created Tokens", tokens);
        return response;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .parseClaimsJws(token)
                .getBody()
                .get("email")
                .toString();
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parser()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return false;
    }
}
