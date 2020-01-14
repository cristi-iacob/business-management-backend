package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Skill {

    private String Id;
    private String name;
    private String area;
    private ItemState itemState;
}
