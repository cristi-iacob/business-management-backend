package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;

import java.util.Objects;

public abstract class BaseCompileChangeStrategy implements CompileChangeStrategy {

    protected FullUserSpecification getUserSpecificationForIdentifier(String identifier) {
        return UserService.getFullUserSpecificationForEmail(identifier);
    }

    protected String getUserHash(String identifier) {
        return String.valueOf(Objects.hash(identifier));
    }
}
