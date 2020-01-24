package ubb.proiectColectiv.businessmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserSkill {

    @JsonProperty
    String skillId;
    @JsonProperty
    String userHash;
}
