package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FullUserSpecification {
    List<Skill> skills;
    List<ProjectExperienceEntry> projectExperience;
    private String consultingLevel;
    private String email;
    private String firstName;
    private String lastName;
    private String region;
}
