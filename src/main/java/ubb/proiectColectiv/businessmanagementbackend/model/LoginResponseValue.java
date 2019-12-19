package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Getter;

@Getter
public enum LoginResponseValue {
    SUCCESSFUL,
    SPAM,
    BLOCKED,
    UNAPPROVED,
    WRONG_PASSWORD,
    INEXISTENT
}
