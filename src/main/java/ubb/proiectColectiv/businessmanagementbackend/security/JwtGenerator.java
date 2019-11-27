package ubb.proiectColectiv.businessmanagementbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import ubb.proiectColectiv.businessmanagementbackend.model.security.JwtUser;

@Component
public class JwtGenerator {
    public String generate(JwtUser jwtUser) {
        Claims claims = Jwts.claims().setSubject(jwtUser.getEmail());
        claims.put("password", jwtUser.getPassword());

        return Jwts.builder().setClaims(claims) //returns generated token
                .signWith(SignatureAlgorithm.HS512, "proiectColectiv")
                .compact();
    }
}
