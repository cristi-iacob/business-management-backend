package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Data;

@Data
public class Skill {

    public Skill() {

    }

    public Skill(String name, String area) {
        this.name =  name;
        this.area = area;
    }
    private String name;
    private String area;
}
