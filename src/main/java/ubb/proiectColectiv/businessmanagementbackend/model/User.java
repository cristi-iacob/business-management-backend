package ubb.proiectColectiv.businessmanagementbackend.model;

public class User {

    private Boolean approvedStatus;
    private Boolean blockedStatus;
    private Integer consultingLevelId;
    private String email;
    private Integer failedLoginCounter;
    private String firstName;
    private String lastName;
    private String password;
    private String profilePicture;
    private Integer regionId;
    private Integer roleId;
    private Integer supervisorId;

    public Boolean getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public Boolean getBlockedStatus() {
        return blockedStatus;
    }

    public void setBlockedStatus(Boolean blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    public Integer getConsultingLevelId() {
        return consultingLevelId;
    }

    public void setConsultingLevelId(Integer consultingLevelId) {
        this.consultingLevelId = consultingLevelId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFailedLoginCounter() {
        return failedLoginCounter;
    }

    public void setFailedLoginCounter(Integer failedLoginCounter) {
        this.failedLoginCounter = failedLoginCounter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

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
