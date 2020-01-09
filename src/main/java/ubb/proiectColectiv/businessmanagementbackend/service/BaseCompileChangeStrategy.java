package ubb.proiectColectiv.businessmanagementbackend.service;

import ubb.proiectColectiv.businessmanagementbackend.model.FullUserSpecification;

public abstract class BaseCompileChangeStrategy implements CompileChangeStrategy {

    protected FullUserSpecification getUserSpecificationForIdentifier(String identifier) {
        return UserService.getFullUserSpecificationForEmail(identifier);
    }
}
