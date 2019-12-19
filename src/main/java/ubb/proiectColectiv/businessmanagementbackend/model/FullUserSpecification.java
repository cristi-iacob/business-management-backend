package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullUserSpecification {

    List<Skill> skills;
    List<ProjectExperienceEntry> projectExperience;
    private Boolean approvedStatus;
    private Boolean blockedStatus;
    private String consultingLevel;
    private String email;
    private Integer failedLoginCounter;
    private String firstName;
    private String lastName;
    private String password;
    private String profilePicture;
    private String region;
    private String role;
    private String supervisor;
    public FullUserSpecification() {
    }
}
