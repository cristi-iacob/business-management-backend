package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum LoginResponseValue {
    SUCCESSFUL,
    SPAM,
    BLOCKED,
    UNAPPROVED,
    WRONG_PASSWORD,
    INEXISTENT
}
