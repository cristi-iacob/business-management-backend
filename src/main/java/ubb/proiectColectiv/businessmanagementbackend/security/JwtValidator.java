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
            //TODO extract email and password
            jwtUser.setUsername(body.getSubject()); //extracting username from token
            jwtUser.setId(String.valueOf(Objects.hash(body.get("userId"))));    //extracting userId from token
            jwtUser.setRole((String) body.get("role"));     //extracting role from token
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return jwtUser;
    }
}
