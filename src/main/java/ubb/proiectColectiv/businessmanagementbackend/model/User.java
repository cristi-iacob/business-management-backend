package ubb.proiectColectiv.businessmanagementbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Boolean approvedStatus;
    private Boolean blockedStatus;
    private Integer consultingLevelId;
    private String email;
    private String hashedEmail;
    private Integer failedLoginCounter;
    private String firstName;
    private String lastName;
    private String password;
    private String profilePicture;
    private Integer regionId;
    private Integer roleId;
    private Integer supervisorId;

    @Override
    public String toString() {
        return "User{ \n" +
                "\tapprovedStatus=" + approvedStatus +
                "\tconsultingLevelId=" + consultingLevelId +
                "\temail='" + email + '\'' +
                "\tfailedLoginCounter=" + failedLoginCounter +
                "\tfirstName='" + firstName + '\'' +
                "\tlastName='" + lastName + '\'' +
                "\tpassword='" + password + '\'' +
                "\tprofilePicture='" + profilePicture + '\'' +
                "\tregionId=" + regionId +
                "\troleId=" + roleId +
                "\tsupervisorId=" + supervisorId +
                "\n}";
    }
}
