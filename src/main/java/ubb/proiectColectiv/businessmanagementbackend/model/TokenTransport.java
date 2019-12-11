package ubb.proiectColectiv.businessmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TokenTransport {

    private final String token;
    @JsonIgnore
    private final LoginResponseValue response;
}
