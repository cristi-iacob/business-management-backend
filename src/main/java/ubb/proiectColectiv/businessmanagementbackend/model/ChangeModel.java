package ubb.proiectColectiv.businessmanagementbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder
public class ChangeModel {
    private ChangeType changeType;
    private Resource resource;
    private HashMap<String, Object> args;
}
