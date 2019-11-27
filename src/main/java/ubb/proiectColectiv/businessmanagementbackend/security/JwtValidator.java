package ubb.proiectColectiv.businessmanagementbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import ubb.proiectColectiv.businessmanagementbackend.model.security.JwtUser;

import java.util.Objects;

@Component
public class JwtValidator {

    private String secret = "proiectColectiv"; //assume that the user used this secret to create the token

    public JwtUser validate(String token) {
        //This uses the io.jsonwebtoken dependency for the claims
        JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); //message
            jwtUser = new JwtUser();
            jwtUser.setEmail(body.getSubject()); //extracting email from token
            jwtUser.setPassword((String) body.get("password"));    //extracting password from token
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return jwtUser;
    }
}
