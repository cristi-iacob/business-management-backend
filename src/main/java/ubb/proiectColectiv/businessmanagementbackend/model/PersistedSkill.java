package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersistedSkill {
    private String id;
    private String name;
    private String skillAreaId;
}
