package ubb.proiectColectiv.businessmanagementbackend.model;

public class User {

    private Boolean approved_status;
    private Integer consulting_level_id;
    private String email;
    private Integer failed_login_counter;
    private String first_name;
    private String last_name;
    private String password;
    private String profile_picture;
    private Integer region_id;
    private Integer role_id;
    private Integer supervisor_id;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Boolean approved_status, Integer consulting_level_id, String email, Integer failed_login_counter, String first_name, String last_name, String password, String profile_picture, Integer region_id, Integer role_id, Integer supervisor_id) {
        this.approved_status = approved_status;
        this.consulting_level_id = consulting_level_id;
        this.email = email;
        this.failed_login_counter = failed_login_counter;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.profile_picture = profile_picture;
        this.region_id = region_id;
        this.role_id = role_id;
        this.supervisor_id = supervisor_id;
    }

    public Boolean getApproved_status() {
        return approved_status;
    }

    public void setApproved_status(Boolean approved_status) {
        this.approved_status = approved_status;
    }

    public Integer getConsulting_level_id() {
        return consulting_level_id;
    }

    public void setConsulting_level_id(Integer consulting_level_id) {
        this.consulting_level_id = consulting_level_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFailed_login_counter() {
        return failed_login_counter;
    }

    public void setFailed_login_counter(Integer failed_login_counter) {
        this.failed_login_counter = failed_login_counter;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(Integer supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    @Override
    public String toString() {
        return "User{ \n" +
                "\tapproved_status=" + approved_status +
                "\tconsulting_level_id=" + consulting_level_id +
                "\temail='" + email + '\'' +
                "\tfailed_login_counter=" + failed_login_counter +
                "\tfirst_name='" + first_name + '\'' +
                "\tlast_name='" + last_name + '\'' +
                "\tpassword='" + password + '\'' +
                "\tprofile_picture='" + profile_picture + '\'' +
                "\tregion_id=" + region_id +
                "\trole_id=" + role_id +
                "\tsupervisor_id=" + supervisor_id +
                "\n}";
    }
}
