package ubb.proiectColectiv.businessmanagementbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.model.security.JwtUser;
import ubb.proiectColectiv.businessmanagementbackend.security.JwtGenerator;

@RestController
@RequestMapping("/token")
//@RequestMapping("proiectColectiv/token")
public class TokenController {

    @Autowired
    private JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public String generate(@RequestBody final JwtUser jwtUser) {
        return jwtGenerator.generate(jwtUser);
    }
}
