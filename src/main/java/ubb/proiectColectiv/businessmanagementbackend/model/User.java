package ubb.proiectColectiv.businessmanagementbackend.model;


import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private Integer role_id;
    private Integer supervisor_id;
    private String profile_picture;
    private Integer consulting_level_id;
    private Integer region_id;
    private Boolean approved_status;
    private Integer failed_login_counter;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String first_name, String last_name, Integer role_id, Integer supervisor_id, String profile_picture, Integer consulting_level_id, Integer region_id, Boolean approved_status, Integer failed_login_counter) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role_id = role_id;
        this.supervisor_id = supervisor_id;
        this.profile_picture = profile_picture;
        this.consulting_level_id = consulting_level_id;
        this.region_id = region_id;
        this.approved_status = approved_status;
        this.failed_login_counter = failed_login_counter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public Integer getConsulting_level_id() {
        return consulting_level_id;
    }

    public void setConsulting_level_id(Integer consulting_level_id) {
        this.consulting_level_id = consulting_level_id;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer region_id) {
        this.region_id = region_id;
    }

    public Boolean getApproved_status() {
        return approved_status;
    }

    public void setApproved_status(Boolean approved_status) {
        this.approved_status = approved_status;
    }

    public Integer getFailed_login_counter() {
        return failed_login_counter;
    }

    public void setFailed_login_counter(Integer failed_login_counter) {
        this.failed_login_counter = failed_login_counter;
    }
}
